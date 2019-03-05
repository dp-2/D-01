
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m where m.userAccount.id =?1")
	Member findMemberByUserAcountId(int userAccountId);

	@Query("select e.member from Enroll e where e.id =?1")
	Member findMemberByEnrollId(int enrollId);

	//Stats number of members per brotherhood
	@Query("select max(1.0*(select count(e.member) from Enroll e where e.brotherhood.id = b.id) ) from Brotherhood b")
	Double maxMembersPerBrotherhood();

	@Query("select min(1.0*(select count(e.member) from Enroll e where e.brotherhood.id = b.id) ) from Brotherhood b")
	Double minMembersPerBrotherhood();

	@Query("select avg(1.0*(select count(e.member) from Enroll e where e.brotherhood.id = b.id) ) from Brotherhood b")
	Double avgMembersPerBrotherhood();

	@Query("select stddev(1.0 * (select count(*) from Enroll a where a.brotherhood = b.id)) from Brotherhood b")
	Double stdevMembersPerBrotherhood();

	@Query("select e.brotherhood.id,e.brotherhood.title, count(e) from Enroll e group by e.brotherhood order by 1 desc")
	Collection<Brotherhood> listBrotherhoodByMembers();

}
