
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p.name from Position p")
	Collection<Position> findPositionName();

	@Query("select p from Position p where p.language = 'es'")
	Collection<Position> findPositionES();

	@Query("select p from Position p where p.language = 'en'")
	Collection<Position> findPositionEN();

	@Query("select count(*) from Position where language='es'")
	Double countTotal();

	@Query("select count(*) from Position where name='president'")
	Double countPresident();

	@Query("select count(*) from Position where name='vicepresident'")
	Double countVicepresident();

	@Query("select count(*) from Position where name='secretary'")
	Double countSecretary();

	@Query("select count(*) from Position where name='treasurer'")
	Double countTreasurer();

	@Query("select count(*) from Position where name='historian'")
	Double countHistorian();

	@Query("select count(*) from Position where name='officer'")
	Double countOfficer();
}
