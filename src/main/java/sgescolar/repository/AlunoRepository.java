package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> { 
			
	@Query( "select a from Aluno a join a.usuario u where u.id=?1" )
	public Optional<Aluno> buscaPorUID( Long logadoUID );
	
	@Query( "select a from Aluno a join a.pessoa p where lower_unaccent(p.nome) like lower_unaccent(?1)" )	
	public List<Aluno> filtra( String nomeIni );
	
}
