package sgescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sgescolar.model.Planejamento;

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {

}
