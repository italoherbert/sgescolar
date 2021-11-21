package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Escola;
import sgescolar.repository.filtro.EscolaFiltroRepository;

public interface EscolaRepository extends JpaRepository<Escola, Long>, EscolaFiltroRepository {

	@Query( "select e from Escola e where lower(e.nome)=lower(?1)" )
	public Optional<Escola> buscaPorNome( String nome );
		
	@Query( "select e from Secretario s join s.escola e where s.id=?1")
	public List<Escola> buscaPorSecretarioId( Long secretarioId );
	
}
