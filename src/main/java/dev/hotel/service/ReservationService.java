package dev.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Reservation;
import dev.hotel.entite.ReservationJson;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.ReservationRepository;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private ClientRepository clientRepository;
	private ChambreRepository chambreRepository;
	
	public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository,
			ChambreRepository chambreRepository) {
		super();
		this.reservationRepository = reservationRepository;
		this.clientRepository = clientRepository;
		this.chambreRepository = chambreRepository;
	}
	
	public String creerReservation (LocalDate dateDebut, LocalDate dateFin, List<UUID> chambresId, UUID clientId) {
		
		Reservation resa = new Reservation();
		String message = "";
		
		if(dateDebut.isBefore(dateFin)){
			resa.setDateDebut(dateDebut);
			resa.setDateFin(dateFin);
			resa.setClient(clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("client non trouvé")));
			
			List<Chambre> listeChambres = chambresId.stream()
					.map(chambreId -> chambreRepository.findById(chambreId)
							.orElseThrow(() -> new EntityNotFoundException("la chambre " + chambreId + " n'existe pas")))
					.collect(Collectors.toList());
					
			resa.setChambres(listeChambres);
	
			if(this.reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin,listeChambres)){
				message = "Les chambres sont déjà réservées à ces dates";
			}else{
				message = "La réservation est enregistrée";
				this.reservationRepository.save(resa);
			}
			
		}else{
			message = "La date de fin doit être supérieure à la date du début";
		}
		return message;
		
		
	}
	
	public List<Reservation> listerReservations() {
		return this.reservationRepository.findAll((Sort.by(Sort.Direction.ASC, "dateDebut")));
	}

}
