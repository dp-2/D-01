
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DFloat;

@Repository
public interface DFloatRepository extends JpaRepository<DFloat, Integer> {

	@Query("select c from DFloat c where c.brotherhood.id = ?1")
	Collection<DFloat> SearchDFloatsByBrotherhood(Integer brotherhoodId);

	@Query("select c from DFloat c where c.brotherhood IS NULL")
	Collection<DFloat> SearchDFloatsWithoutBrotherhood();

	//	@Query("select d from DFloat d where d.procession.id =?1")
	//	List<DFloat> findDFloatsByProcessionId(int processionId);
}
