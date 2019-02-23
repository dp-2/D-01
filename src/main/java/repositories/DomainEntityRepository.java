
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DFloat;

@Repository
public interface DomainEntityRepository extends JpaRepository<DFloat, Integer> {

}
