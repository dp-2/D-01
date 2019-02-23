
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Enroll;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Integer> {

}
