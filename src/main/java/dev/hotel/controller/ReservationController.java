package dev.hotel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.entite.ReservationJson;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

	private ReservationRepository reservationRepository;
	private ClientRepository clientRepository;
	private ChambreRepository chambreRepository;
	
	public ReservationController(ReservationRepository reservationRepository, ClientRepository clientRepository,
			ChambreRepository chambreRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.clientRepository = clientRepository;
		this.chambreRepository = chambreRepository;
	}


	// POST /reservations
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ReservationJson reservationJson) {

		UUID idClient = reservationJson.getClientId();
	
		if (this.clientRepository.findById(idClient).isPresent()) {
			
			List<UUID> listIdChambres = reservationJson.getChambres();
			
			List<Chambre> listeChambres = new ArrayList<>();
			
			String chambreNotExist = "";
			
			for(UUID idCh : listIdChambres){
				if(this.chambreRepository.existsById(idCh)){
					
					Chambre chambre = this.chambreRepository.findById(idCh)
							.orElseThrow(() -> new EntityNotFoundException("Chambre non trouvée"));
					
					listeChambres.add(chambre);
				}else{
					String separateur = ",";
					if(chambreNotExist.equals("")){
						separateur = "";
					}
					chambreNotExist = chambreNotExist+separateur+idCh;
				}
			}
			
			if(chambreNotExist.equals("") && listeChambres.size() > 0){
				
				
				Client client = this.clientRepository.findById(idClient)
						.orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
				
				
				Reservation newResa = new Reservation(reservationJson.getDateDebut(),reservationJson.getDateFin(),client,listeChambres);
				newResa.setUuid(UUID.randomUUID());
				this.reservationRepository.save(newResa);
				return ResponseEntity.status(HttpStatus.CREATED).body("La réservation a été enregistrée");
			}else{
				String message = "La chambre "+chambreNotExist+" n'existe pas";
				
				if(chambreNotExist.split(",").length>1){
					 message = "Les chambre "+chambreNotExist+" n'existent pas";
				}
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(message);
			}
			
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Le client "+idClient+" n'existe pas");
		}
	}
	
	// Cette méthode est exécutée lorsqu'une requête GET /reservations
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Reservation> listeReservations() {

		List<Reservation> liste = new ArrayList<>();

		if (this.reservationRepository.findAll().size() > 0) {
			liste = this.reservationRepository.findAll((Sort.by(Sort.Direction.ASC, "dateDebut")));
		}

		return liste;
	}
	
	
	// Cette méthode est exécutée lorsqu'une requête GET /reservations
	@RequestMapping(method = RequestMethod.GET,params={"dateDebut","dateFin","chambres"})
	@ResponseBody
	public boolean reservationsExist(@RequestParam("dateDebut") String dateDebutHttp ,@RequestParam("dateFin") String dateFinHttp, @RequestParam("chambres") List<UUID> chambresHttp)  {
		
		Boolean exist=false;
		LocalDate dateDebut = LocalDate.parse(dateDebutHttp);
		LocalDate dateFin = LocalDate.parse(dateFinHttp);
		
		List<Chambre> listeChambres = new ArrayList<>();
		
		
		for(UUID idCh : chambresHttp){
			if(this.chambreRepository.existsById(idCh)){
				
				Chambre chambre = this.chambreRepository.findById(idCh)
						.orElseThrow(() -> new EntityNotFoundException("Chambre non trouvée"));
				
				listeChambres.add(chambre);
			}
		}
		System.out.println(listeChambres.size());
		List<Reservation> resa = this.reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin,listeChambres);
		
		/*if(resa.size() > 0){
			exist=true;
		}*/
		
		//if (resa.l) {
			//liste = bthis.reservationRepository.findAll((Sort.by(Sort.Direction.ASC, "dateDebut")));
			//exist =true;
		//}

		return exist;
	}
}
