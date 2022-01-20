package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	@Query( "select m from Matricula m join m.aluno a join m.turma t join t.anoLetivo al where a.id=?1 and al.id=?2" )
	public Optional<Matricula> buscaPorAnoLetivo( Long alunoId, Long anoLetivoId );
	
	@Query( "select m from Matricula m join m.aluno a join m.turma t join t.anoLetivo al where a.id=?1 and al.ano=?2" )
	public Optional<Matricula> buscaPorAno( Long alunoId, int ano );
	
	@Query( "select m from Matricula m where m.numero=?1" )
	public Optional<Matricula> buscaPorNumero( String numero );
	
	@Query( "select m from Matricula m join m.aluno a where a.id=?1" )
	public List<Matricula> listaMatriculasPorAlunoID( Long alunoId );
	
	@Query( "select m from Matricula m join m.turma t where t.id=?1" )
	public List<Matricula> listaMatriculasPorTurmaID( Long turmaId );
	
	@Query( "select m from Matricula m join m.aluno a join a.pessoa p join m.turma t "
			+ "where t.id=?1 and lower_unaccent(p.nome) like lower_unaccent(?2)" )
	public List<Matricula> filtraTodas( Long turmaId, String nomeini );
	
	@Query( "select m from Matricula m join m.aluno a join a.pessoa p join m.turma t "
			+ "where t.id=?1 and lower_unaccent(p.nome) like lower_unaccent(?2) and m.encerrada=false" )
	public List<Matricula> filtraAtivas( Long turmaId, String nomeini );
	
}
