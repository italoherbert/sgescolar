package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Professor;
import sgescolar.repository.filtro.ProfessorFiltroRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorFiltroRepository {
				
	@Query( "select p from Professor p join p.funcionario f join f.usuario u where u.id=?1" )
	public Optional<Professor> buscaPorUID( Long logadoUID );
	
}
