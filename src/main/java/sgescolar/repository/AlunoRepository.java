package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Aluno;
import sgescolar.repository.filtro.AlunoFiltroRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, AlunoFiltroRepository { 
	
	@Query( "select a from Aluno a join a.usuario u where u.id=?1" )
	public Optional<Aluno> buscaPorUID( Long logadoUID );
	
}
