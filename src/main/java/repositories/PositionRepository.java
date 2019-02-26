
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;
import domain.SocialProfile;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p.name from Position p p.id = ?1")
	Collection<SocialProfile> findProfileByActorId(Integer actorId);
}
