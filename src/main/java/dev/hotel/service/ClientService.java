package dev.hotel.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@Service
public class ClientService {

	private ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}
	
	public List<Client> listerClients() {
		return this.clientRepository.findAll();
	}

	public List<Client> rechercherClientsParNom(String nomRequete) {
		return this.clientRepository.findByNom(nomRequete);
	}
	
	public UUID creerClient(Client clientRecu) {
		clientRecu.setNom(clientRecu.getNom().toUpperCase());
		if (this.clientRepository.existsByNomAndPrenoms(clientRecu.getNom(), clientRecu.getPrenoms())) {
			throw new EntityExistsException();
		}
		
		return this.clientRepository.save(clientRecu).getUuid();
	}
}
