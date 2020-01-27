package dev.hotel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

	//@Query("select * from Reservation r inner join resa_chambre rc on resa_id = r.id inner join chambre c on rc.chambre_id = c.id where date_debut <= :dateDebut and date_fin >= :dateFin")
	@Query("select r from Reservation r inner join r.chambres where date_debut <= :dateDebut and date_fin >= :dateFin and r.chambres in (:chambres)")
			List<Reservation> findByDateDebutAndDateFin(@Param("dateDebut") LocalDate dateDebut, @Param ("dateFin") LocalDate dateFin, @Param("chambres") List<Chambre> chambres);

	//Reservation findAllBydateDebutLessThanEqualAnddateFinGreaterThanEqual(LocalDate dateDebut, LocalDate dateFin);
	
	
}
