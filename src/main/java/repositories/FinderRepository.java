
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p.finder from Procession p where p.id = ?1")
	Finder findByProcessionId(int processionId);

	@Query("select p from Procession p where (p.ticker like %:keyword% or p.description like %:keyword% and (p.momentOrganised BETWEEN :dateMin and :dateMax) and area.procession.id = p.id)")
	List<Procession> searchProcessions(@Param("keyword") String keyword, @Param("dateMin") Date dateMin, @Param("dateMax") Date dateMax, @Param("area") Area area);
}
