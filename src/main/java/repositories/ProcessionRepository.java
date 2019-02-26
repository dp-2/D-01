
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_DATE")
	List<Procession> findProcessionsFinal();

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_TIME and p.brotherhood.id = ?1")
	List<Procession> findProcessionsFinalByBrotherhoodId(int brotherhoodId);

	@Query("select p from Procession p where p.brotherhood.id = ?1")
	List<Procession> findProcessionsByBrotherhoodId(int brotherhoodId);

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_DATE and p.brotherhood.id = ( select e.brotherhood.id from Enroll e where e.member.id = ?1 ))")
	List<Procession> findProcessionOfMember(int memberId);
}
