
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccount(int userAccountId);

	@Query("select a from Actor a where a.userAccount.username=?1")
	Actor findByUsername(String username);

	@Query("select a from Actor a where a.spammer = true")
	Collection<Actor> findSpammerActors();
	//---------------------Query C1------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the number of fix-up tasks per user

	// TODO solucionar esta query

	//@Query("select min(count(f)),max(count(f)),avg(count(f)),sqrt(sum(count(f) * count(f)) /count(f1) - (avg(count(f)) *avg(count(f)))) from FixupTask f, FixupTask f1 group by f.customer")
	//Double[] fixupTasksStats();

}
