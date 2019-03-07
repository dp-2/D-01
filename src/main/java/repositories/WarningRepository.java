
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Warning;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Integer> {
}
