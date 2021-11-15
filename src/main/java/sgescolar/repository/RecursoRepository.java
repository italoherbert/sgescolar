package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Recurso;
import sgescolar.repository.filtro.RecursoFiltroRepository;

public interface RecursoRepository extends JpaRepository<Recurso, Long>, RecursoFiltroRepository {

	@Query( "select r from Recurso r where lower(r.nome)=lower(?1)")
	public Optional<Recurso> buscaPorNome( String nome );
		
}
