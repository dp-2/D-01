package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.DomainEntity;

public interface DomainEntityRepository extends JpaRepository<DomainEntity, Integer> {

}
