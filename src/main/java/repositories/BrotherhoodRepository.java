
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where b.userAccount.id =?1")
	Brotherhood findBrotherhoodByUserAcountId(int userAccountId);

	@Query("select e.brotherhood from Enroll e where e.status='APPROVED' group by e.brotherhood order by 1 asc")
	List<Brotherhood> listBrotherhoodByMembers();

}
