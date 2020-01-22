package dev.hotel;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Hotel;
import dev.hotel.repository.ChambreRepository;
import dev.hotel.repository.ClientRepository;
import dev.hotel.repository.HotelRepository;

@Component
public class Startup {

	private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

	private ClientRepository clientRepository;
	private ChambreRepository chambreRepository;
	private HotelRepository hotelRepository;

	public Startup(ClientRepository clientRepository, ChambreRepository chambreRepository,
			HotelRepository hotelRepository) {
		super();
		this.clientRepository = clientRepository;
		this.chambreRepository = chambreRepository;
		this.hotelRepository = hotelRepository;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void init() {

		LOG.info("Démarrage de l'application");

		if (this.clientRepository.count() == 0) {
			Client client1 = new Client("PIERRE", "Jean");
			client1.setUuid(UUID.randomUUID());
			this.clientRepository.save(client1);

			Client client2 = new Client("LOPEZ", "Dimitri");
			client2.setUuid(UUID.randomUUID());
			this.clientRepository.save(client2);

			Client client3 = new Client("PIERRE", "Mélanie");
			client3.setUuid(UUID.randomUUID());
			this.clientRepository.save(client3);
		}


		if (this.hotelRepository.count() == 0) {

			UUID idHotel = UUID.randomUUID();
			Hotel hotel = new Hotel("IBIS", 2);
			hotel.setUuid(idHotel);

			this.hotelRepository.save(hotel);

			if (this.chambreRepository.count() == 0) {
				Chambre ch1 = new Chambre("1", new Float(10), hotel);
				this.chambreRepository.save(ch1);

				Chambre ch2 = new Chambre("2", new Float(9.3), hotel);
				this.chambreRepository.save(ch2);

				Chambre ch3 = new Chambre("3", new Float(12.85), hotel);
				this.chambreRepository.save(ch3);

				Chambre ch4 = new Chambre("4", new Float(15.36), hotel);
				this.chambreRepository.save(ch4);

			}
		}

	}

}
