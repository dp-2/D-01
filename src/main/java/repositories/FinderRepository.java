
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f where f.member.id = ?1")
	Finder findFinderByMemberId(int memberId);

	@Query("select min(f.processions.size),max(f.processions.size),avg(f.processions.size),sqrt(((select sum(f1.processions.size) from Finder f1) - (select avg(f2.processions.size) from Finder f2)) * ((select sum(f1.processions.size) from Finder f1) - (select avg(f2.processions.size) from Finder f2)) / count(f)) from Finder f")
	Collection<Double> finderStats();

	//Query: "The ratio of the empty versus non-empty finders" Vamos a considerar un finder vacío cuando no tiene lastUpdate

	//	@Query("select (count(*)*1.0)/(select count(*) from Finder ff where ff.lastUpdate > '2000-1-1') from Finder f where f.lastUpdate <= '2000-1-1'")
	//	Double emptyVSNonEmptyFinder();
}
