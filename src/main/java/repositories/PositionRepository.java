
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	//	@Query("select p.name from Position p")
	//	Collection<Position> findPositionName();
	//
	//	@Query("select p from Position p where p.language = 'es'")
	//	Collection<Position> findPositionES();
	//
	//	@Query("select p from Position p where p.language = 'en'")
	//	Collection<Position> findPositionEN();
	//
	@Query("select count(*) from Position")
	Double countTotal();

	@Query("select count(*) from Position where nameEnglish='President'")
	Double countPresident();

	@Query("select count(*) from Position where nameEnglish='Picepresident'")
	Double countVicepresident();

	@Query("select count(*) from Position where nameEnglish='Secretary'")
	Double countSecretary();

	@Query("select count(*) from Position where nameEnglish='Treasurer'")
	Double countTreasurer();

	@Query("select count(*) from Position where nameEnglish='Historian'")
	Double countHistorian();

	@Query("select count(*) from Position where nameEnglish='Officer'")
	Double countOfficer();

}
