package dev.hotel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

	private ClientRepository clientRepository;

	public ClientController(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /clients est reçue
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody // parser l'objet Client
	public List<Client> retournePlusieursClients() {

		List<Client> liste = new ArrayList<>();

		if (this.clientRepository.findAll().size() > 0) {
			liste = this.clientRepository.findAll();
		}

		return liste;
	}

	// requete GET clients?nom=XXX , methode executee avec url clients avec
	// parametre
	@RequestMapping(method = RequestMethod.GET, params = "nom")
	@ResponseBody // parser l'objet Client
	public List<Client> listeClientsParNom(@RequestParam("nom") String nomRequeteHttp) {

		List<Client> liste = new ArrayList<>();

		if (!this.clientRepository.findByNom(nomRequeteHttp.toUpperCase()).isEmpty()) {
			liste = this.clientRepository.findByNom(nomRequeteHttp.toUpperCase());
		}
		return liste;
	}

	// POST /clients
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody Client client) {

		if (this.clientRepository.findByNomAndPrenoms(client.getNom(), client.getPrenoms()).isEmpty()) {
			Client newClient = new Client(client.getNom(), client.getPrenoms());
			newClient.setUuid(UUID.randomUUID());
			this.clientRepository.save(newClient);
			return ResponseEntity.status(HttpStatus.CREATED).body("Le client est inséré");
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Un client de même nom et prénoms est déjà enregistré");
		}
	}

}
