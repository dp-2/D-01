
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enroll;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Integer> {

	@Query("select e from Enroll e where e.member.id = ?1")
	Collection<Enroll> findEnrollByMember(int memberId);

	@Query("select e from Enroll e where e.procession.id = ?1")
	Collection<Enroll> findEnrollByProcession(int processionId);
}
