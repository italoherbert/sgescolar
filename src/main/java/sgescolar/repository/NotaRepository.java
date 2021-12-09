package sgescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sgescolar.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {

}
