
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	//QUERY B1
	//TODO Esta query no funciona bien.
	@Query("select count(a.brotherhood),avg(a.brotherhood), min(a.brotherhood), max(a.brotherhood), stddev(a.brotherhood) from Area a")
	Double[] queryC2AVG();

	@Query("select a from Area a where a.brotherhood.id = ?1")
	Area findAreaByBrotherhoodId(int brotherhoodId);

	//	@Query("(select count(a.brotherhood) from Area a)/(select count(*) from Area a)")  GUARDAR POR SI ACASO
	@Query("select avg(1.0 * (select count(*) from Area a where a.brotherhood = b.id)) from Brotherhood b")
	Double avgHermandadesPorArea();

	@Query("select min(1.0 * (select count(*) from Area a where a.brotherhood = b.id)) from Brotherhood b")
	Double minHermandadesPorArea();

	@Query("select max(1.0 * (select count(*) from Area a where a.brotherhood = b.id)) from Brotherhood b")
	Double maxHermandadesPorArea();

	@Query("select stddev(1.0 * (select count(*) from Area a where a.brotherhood = b.id)) from Brotherhood b")
	Double stddevHermandadesPorArea();
}
