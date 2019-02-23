
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DFloat;

@Repository
public interface DFloatRepository extends JpaRepository<DFloat, Integer> {

}
