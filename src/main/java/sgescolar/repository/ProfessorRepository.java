package sgescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	@Query( "select pr from Professor pr join pr.funcionario f join f.pessoa p where lower(p.nome) like lower(?1)" )
	public List<Professor> filtra( String nomeIni );
		
	@Query( "select count(*) from Professor p join p.funcionario f join f.usuario u where u.id=?1" )
	public boolean verificaSeDono( Long logadoUID );
	
}
