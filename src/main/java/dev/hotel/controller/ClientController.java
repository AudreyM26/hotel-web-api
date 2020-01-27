package dev.hotel.controller;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.service.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

	private ClientService clientService;

	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	// Cette méthode est exécutée lorsqu'une requête GET /clients est reçue
	@GetMapping
	public List<Client> listerClients() {
		return this.clientService.listerClients();
	}

	// requete GET clients?nom=XXX , methode executee avec url clients avec
	// parametre
	@GetMapping(params = "nom")
	public List<Client> rechercherClientsParNom(@RequestParam("nom") String nomRequeteHttp) {
		return this.clientService.rechercherClientsParNom(nomRequeteHttp);
	}

	// POST /clients
	@PostMapping
	public UUID creerClient(@RequestBody @Valid Client clientPost) {
		return this.clientService.creerClient(clientPost);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> validationException(MethodArgumentNotValidException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(value = { EntityExistsException.class })
	public ResponseEntity<String> ClientPresent(EntityExistsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("client déjà existant");
	}
}
