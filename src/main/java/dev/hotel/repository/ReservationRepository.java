package dev.hotel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.hotel.entite.Reservation;
import dev.hotel.entite.Chambre;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

	@Query("select case when (count(*) >0) then true else false end from Reservation r inner join r.chambres c where date_debut <= :dateFin and date_fin >= :dateDebut and c in (:chambres)")
	Boolean findByDateDebutAndDateFin(@Param("dateDebut") LocalDate dateDebut,
			@Param("dateFin") LocalDate dateFin, @Param("chambres") List<Chambre> chambres);

}
