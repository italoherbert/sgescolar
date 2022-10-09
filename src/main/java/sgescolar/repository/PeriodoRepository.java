package sgescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Periodo;

public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

	@Query( "select p from Periodo p join p.anoLetivo al where al.id=?1" )
	public List<Periodo> listaPorAnoLetivo( Long anoLetivoId );
	
}
