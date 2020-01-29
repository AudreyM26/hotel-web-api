package dev.hotel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.hotel.entite.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, UUID> {

	@Query("select c FROM Chambre c where c.id not in (SELECT rc FROM Reservation r inner join r.chambres rc  WHERE date_debut >= :dateDebut and date_fin <= :dateFin) group by c.id")
			List<Chambre> findByChambresDispos(@Param("dateDebut") LocalDate dateDebut,
					@Param("dateFin") LocalDate dateFin);
}
