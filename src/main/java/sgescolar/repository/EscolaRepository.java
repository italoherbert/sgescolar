package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Escola;

public interface EscolaRepository extends JpaRepository<Escola, Long> {
	
	@Query( "select e from Escola e where lower_unaccent(e.nome)=lower_unaccent(?1)" )
	public Optional<Escola> buscaPorNome( String nome );
			
	@Query( "select e from Escola e join e.instituicao i where i.id=?1 and lower_unaccent(e.nome) like lower_unaccent(?2)" )
	public List<Escola> filtra( Long instituicaoId, String nomeIni );
	
	@Query( "select e from Escola e join e.instituicao i where i.id=?1" )
	public List<Escola> lista( Long instituicaoId );
	
}
