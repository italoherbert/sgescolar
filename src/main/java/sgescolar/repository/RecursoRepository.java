package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Recurso;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

	@Query( "select r from Recurso r where lower_unaccent(r.nome)=lower_unaccent(?1)")
	public Optional<Recurso> buscaPorNome( String nome );
		
	@Query( "select r from Recurso r where lower_unaccent(r.nome) like lower_unaccent(?1)")	
	public List<Recurso> filtra( String nomeIni );
	
}
