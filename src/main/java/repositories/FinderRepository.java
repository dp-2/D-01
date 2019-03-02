
package repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where (p.ticker like %:keyword% or p.description like %:keyword% and (p.momentOrganised BETWEEN :dateMin and :dateMax))")
	List<Procession> searchProcessions(@Param("keyword") String keyword, @Param("dateMin") Date dateMin, @Param("dateMax") Date dateMax);

	@Query("select f from Finder f where f.member.id = ?1")
	Finder findFinderByMemberId(int memberId);

	//Query: "The ratio of the empty versus non-empty finders" Vamos a considerar un finder vacío cuando no tiene lastUpdate

	@Query("select (count(*)*1.0)/(select count(*) from Finder ff where ff.last_Update > '2000-1-1') from Finder f where f.last_update <= '2000-1-1'")
	Double emptyVSNonEmptyFinder();
}
