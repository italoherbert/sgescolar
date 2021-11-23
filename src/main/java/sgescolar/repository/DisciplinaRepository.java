package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

	@Query( "select d from Disciplina d join d.turma t where t.id=?1 and lower(d.descricao)=lower(?2)")
	public Optional<Disciplina> buscaPorDescricao( Long turmaId, String descricao );
	
	@Query( "select d from Disciplina d join d.turma t where t.id=?1 and lower(d.descricao) like lower(?2)")
	public List<Disciplina> filtra( Long turmaId, String descricaoIni );
	
	@Query( "select d from Disciplina d join d.turma t where t.id=?1" )
	public List<Disciplina> lista( Long turmaId );
	
}
