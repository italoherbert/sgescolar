package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
				
	@Query( "select pr from Professor pr join pr.funcionario f join f.usuario u where u.id=?1" )
	public Optional<Professor> buscaPorUID( Long logadoUID );
	
	@Query( "select pr from Professor pr join pr.funcionario f join f.pessoa p where lower_unaccent(p.nome) like lower_unaccent(?1)")
	public List<Professor> filtra( String nomeIni, Pageable p );
		
}
