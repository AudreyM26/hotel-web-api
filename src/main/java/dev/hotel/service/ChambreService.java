package dev.hotel.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.repository.ChambreRepository;

@Service
public class ChambreService {

	private ChambreRepository chambreRepository;

	public ChambreService(ChambreRepository chambreRepository) {
		super();
		this.chambreRepository = chambreRepository;
	}
	
	public List<Chambre> listerChambres() {
		return this.chambreRepository.findAll((Sort.by(Sort.Direction.ASC, "numero")));
	}

}
