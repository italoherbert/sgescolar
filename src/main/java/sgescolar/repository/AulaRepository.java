package sgescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sgescolar.model.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {
	
}
