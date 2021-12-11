package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.BNCCHabilidade;

public interface BNCCHabilidadeRepository extends JpaRepository<BNCCHabilidade, String> {
	
	public Optional<BNCCHabilidade> findByCodigo( String codigo );

	public boolean existsByCodigo( String codigo );
	
	@Query( "select h from BNCCHabilidade h where lower_unaccent(h.codigo) like lower_unaccent(?1) order by h.codigo" )
	public List<BNCCHabilidade> filtra( String codigoIni, Pageable p );
	
}
