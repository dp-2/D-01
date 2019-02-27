
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m where m.userAccount.id =?1")
	Member findMemberByUserAcountId(int userAccountId);

	@Query("select e.member from Enroll e where e.id =?1")
	Member findMemberByEnrollId(int enrollId);
}
