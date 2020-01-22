package dev.hotel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Chambre;
import dev.hotel.repository.ChambreRepository;

@RestController
@RequestMapping(value = "/chambres")
public class ChambreController {

	private ChambreRepository chambreRepository;

	public ChambreController(ChambreRepository chambreRepository) {
		super();
		this.chambreRepository = chambreRepository;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /clients est reçue
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Chambre> listeChambres() {

		List<Chambre> liste = new ArrayList<>();

		if (this.chambreRepository.findAll().size() > 0) {
			liste = this.chambreRepository.findAll((Sort.by(Sort.Direction.ASC, "numero")));
		}

		return liste;
	}
}
