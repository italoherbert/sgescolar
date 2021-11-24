package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Escola;

public interface EscolaRepository extends JpaRepository<Escola, Long> {
	
	@Query( "select e from Escola e where lower_unaccent(e.nome)=lower_unaccent(?1)" )
	public Optional<Escola> buscaPorNome( String nome );
		
	@Query( "select e from Secretario s join s.escola e where s.id=?1")
	public List<Escola> buscaPorSecretarioId( Long secretarioId );

	@Query( "select e from Escola e where lower_unaccent(e.nome) like lower_unaccent(?1)" )
	public List<Escola> filtra( String nomeIni );
	
}
