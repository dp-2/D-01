
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Enroll;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Integer> {

	@Query("select e from Enroll e where e.member.id = ?1")
	Collection<Enroll> findEnrollByMember(int memberId);

	@Query("select e.brotherhood from Enroll e where e.member.id != ?1")
	Collection<Brotherhood> findBrotherhoodByMember(int memberId);

	@Query("select e from Enroll e where e.brotherhood.id = ?1 and e.status='PENDING'")
	Collection<Enroll> findEnrollByBrotherhood(int brotherhoodId);
}
