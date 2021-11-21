package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.AnoLetivo;

public interface AnoLetivoRepository extends JpaRepository<AnoLetivo, Long> {

	@Query( "select al from AnoLetivo al join al.escola e where e.id=?1 and al.id=?2")
	public Optional<AnoLetivo> buscaPorId( Long escolaId, Long anoLetivoId );

	@Query( "select al from AnoLetivo al join al.escola e where e.id=?1 and al.ano=?2")
	public Optional<AnoLetivo> buscaPorAno( Long escolaId, int ano );
	
	@Query( "select al from AnoLetivo al join al.escola e where e.id=?1" )
	public List<AnoLetivo> buscaTodosPorEscola( Long escolaId );
	
}
