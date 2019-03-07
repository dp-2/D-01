
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_DATE")
	List<Procession> findProcessionsFinal();

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_DATE and p.brotherhood.id = ?1")
	List<Procession> findProcessionsFinalByBrotherhoodId(int brotherhoodId);

	@Query("select p from Procession p where p.brotherhood.id = ?1")
	List<Procession> findProcessionsByBrotherhoodId(int brotherhoodId);

	@Query("select p from Procession p where p.ffinal = true and p.momentOrganised > CURRENT_DATE and p.brotherhood.id = ( select e.brotherhood.id from Enroll e where e.member.id = ?1 ))")
	List<Procession> findProcessionOfMember(int memberId);

	//The processions that are going to be organised in 30 days or less.
	@Query("select p from Procession p where p.momentOrganised BETWEEN CURRENT_DATE and :currentDayPlus30Days")
	List<Procession> findProcessionsIn30Days(@Param("currentDayPlus30Days") Date date);

	@Query("select p from Procession p, DFloat f where p.brotherhood.id = f.brotherhood.id and p in f.processions and f.id = ?1")
	List<Procession> findProcessionsForRemove(Integer dfloatId);

	@Query("select p from Procession p, DFloat f where p.brotherhood.id = f.brotherhood.id and p not in f.processions and f.id = ?1")
	List<Procession> findProcessionsForAdd(Integer dfloatId);

}
