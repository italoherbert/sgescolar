package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.TurmaDisciplina;

public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

	@Query( "select td "
			+ "from TurmaDisciplina td join td.disciplina d join td.turma t "
			+ "where t.id=?1 and d.id=?2" )
	public Optional<TurmaDisciplina> buscaPorVinculoIDs( Long turmaId, Long disciplinaId );
	
	@Query( "select td from TurmaDisciplina td join td.turma t where t.id=?1" )
	public List<TurmaDisciplina> listaPorTurma( Long turmaId );
	
	@Query( "select td from TurmaDisciplina td "
			+ "join td.professorAlocacao pral join pral.professor pr join td.turma t join t.anoLetivo al "
		    + "where pr.id=?1 and al.ano=?2 and pral.ativo=true" )
	public List<TurmaDisciplina> listaPorProfessor( Long professorId, int ano );
	
	@Query( "select td from TurmaDisciplina td "
			+ "join td.turma t join t.matriculas m join m.aluno a join t.anoLetivo al "
		    + "where a.id=?1 and al.ano=?2" )
	public List<TurmaDisciplina> listaPorAluno( Long alunoId, int ano );
	
	@Query( "select td from TurmaDisciplina td "
			+ "join td.turma t join td.professorAlocacao pral join pral.professor pr join t.anoLetivo al "
			+ "where t.id=?1 and pr.id=?2 and al.ano=?3 and pral.ativo=true")
	public List<TurmaDisciplina> listaPorTurmaEProfessor( Long turmaId, Long professorId, int ano );
	
}
