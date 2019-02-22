
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DomainEntity;

@Repository
public interface DomainEntityRepository extends JpaRepository<DomainEntity, Integer> {

}
