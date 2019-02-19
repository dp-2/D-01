
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.March;

@Repository
public interface MarchRepository extends JpaRepository<March, Integer> {

	@Query("select a from March a where a.handyWorker.id = ?1")
	Collection<March> findMarchsByHandyWorker(int handyWorkerId);

	@Query("select a from March a where a.fixupTask.id = ?1")
	Collection<March> findMarchsByFixupTask(int fixupTaskId);

	//---------------------Query C4------------------------------
	//The average, the minimum, the maximum, and the standard deviation of the
	//price offered in the marchs

	@Query("select min(a.price),max(a.price),avg(a.price),sqrt(sum(a.price * a.price) /count(a.price) - (avg(a.price) *avg(a.price))) from March a")
	Double[] marchPriceStats();

	//------------------------Query C5----------------------------------------
	//The ratio of pending marchs.

	@Query("select (select count(*) from a where status='PENDING' )/count(*)*100 from March a")
	Double pendingRatio();

	//------------------------Query C6----------------------------------------
	//The ratio of accepted marchs.

	@Query("select (select count(*) from a where status='ACCEPTED' )/count(*)*100 from March a")
	Double acceptedRatio();

	//------------------------Query C7----------------------------------------
	//The ratio of rejected marchs.

	@Query("select count(app)/(select count(ap) from March ap)from March app where app.status='REJECTED'")
	Double appsRejectedRatio();

	//------------------------Query C8----------------------------------------
	//The ratio of pending marchs that cannot change its status because their time period elapsed.

	@Query("select 100*(count(app)/(select count(ap) from March ap))from March app where app.status='PENDING' and app.fixupTask.end < CURRENT_TIMESTAMP ")
	Double lateMarchsRatio();

}
