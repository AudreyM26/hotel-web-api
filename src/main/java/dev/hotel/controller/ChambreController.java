package dev.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Chambre;
import dev.hotel.service.ChambreService;

@RestController
@RequestMapping(value = "/chambres")
public class ChambreController {

	private ChambreService chambreService;

	public ChambreController(ChambreService chambreService) {
		super();
		this.chambreService = chambreService;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /chambres est reçue
	@GetMapping
	public List<Chambre> listerChambres() {
		return this.chambreService.listerChambres();
	}
	
	@GetMapping(params={"dateDebut","dateFin"})
	public List<Chambre> listerChambresDispos(@RequestParam("dateDebut") String dateDebutHttp ,@RequestParam("dateFin") String dateFinHttp) {
		LocalDate dateDebut = LocalDate.parse(dateDebutHttp);
		LocalDate dateFin = LocalDate.parse(dateFinHttp);
		
		return this.chambreService.listerChambresDispos(dateDebut,dateFin);
	}
}
