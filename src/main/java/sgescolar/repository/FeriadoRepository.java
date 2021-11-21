package sgescolar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Feriado;
import sgescolar.repository.filtro.FeriadoFiltroRepository;

public interface FeriadoRepository extends JpaRepository<Feriado, Long>, FeriadoFiltroRepository {

	@Query( "select f from Feriado f join f.anoLetivo al where al.id=?1 and ?2 between f.dataInicio and f.dataFim" )
	public Optional<Feriado> buscaPorData( Long anoLetivoId, Date dataDia );
		
	@Query( "select f from Feriado f join f.anoLetivo al where al.id=?1")
	public List<Feriado> listaPorAnoLetivo( Long anoLetivoId );
	
}
