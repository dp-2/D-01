
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialProfile;

@Repository
public interface SocialProfileRepository extends JpaRepository<SocialProfile, Integer> {

	@Query("select sp from SocialProfile sp where sp.actor.id = ?1")
	Collection<SocialProfile> findProfileByActorId(Integer actorId);

}
