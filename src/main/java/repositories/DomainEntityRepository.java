
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DomainEntity;

@Repository
public interface DomainEntityRepository extends JpaRepository<DomainEntity, Integer> {

	@Query("select d from DomainEntity d where d.id = ?1")
	DomainEntity findById(int id);

}
