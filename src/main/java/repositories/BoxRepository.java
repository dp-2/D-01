
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b where b.actor.id = ?1")
	List<Box> findBoxsByActor(Integer actorId);

	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2")
	Box findBoxByActorAndUsername(Integer actorId, String name);

	@Query("select b from Box b where b.root.id = ?1")
	Collection<Box> findByRootId(Integer rootId);

	@Query("select b from Box b where b.actor.id = ?1 and b.root.id = ?2")
	Collection<Box> findByActorIdAndRootId(Integer actorId, Integer rootId);

	@Query("select b from Box b where b.actor.id = ?1 and (b.root.id = b.id or b.root = NULL)")
	Collection<Box> findByActorIdWithoutRoot(Integer actorId);

}
