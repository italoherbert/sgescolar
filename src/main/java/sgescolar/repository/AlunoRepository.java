package sgescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	@Query( "select a from Aluno a join a.pessoa p where lower(p.nome) like lower(?1)" )
	public List<Aluno> filtra( String nomeIni );
	
}
