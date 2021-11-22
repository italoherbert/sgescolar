package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Curso;
import sgescolar.repository.filtro.CursoFiltroRepository;

public interface CursoRepository extends JpaRepository<Curso, Long>, CursoFiltroRepository { 

	@Query( "select c from Curso c join c.escola e where e.id=?1 and lower(c.nome)=lower(?2)" )
	public Optional<Curso> buscaPorNome( Long escolaId, String nome );
		
}
