package dev.hotel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import dev.hotel.service.ReservationService;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

	private ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}

	// POST /reservations
	@PostMapping
	public ResponseEntity<String> creerReservation(@RequestBody @Valid ReservationJson reservationJson) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(this.reservationService.creerReservation(reservationJson.getDateDebut(),
						reservationJson.getDateFin(), reservationJson.getChambres(), reservationJson.getClientId()));
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<String> reservationPresent(EntityNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur " + exception.getMessage());
	}

	// GET /reservations
	@GetMapping
	public List<Reservation> listerReservations() {
		return this.reservationService.listerReservations();
	}

}
