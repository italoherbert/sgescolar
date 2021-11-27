package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.ProfessorAlocacao;

public interface ProfessorAlocacaoRepository extends JpaRepository<ProfessorAlocacao, Long>{

	@Query( "select pa from ProfessorAlocacao pa "
				+ "join pa.turmaDisciplina td join td.turma t join td.disciplina d join pa.professor p "
			+ "where t.id=?1 and d.id=?2 and p.id=?3" )
	public Optional<ProfessorAlocacao> buscaVinculoPorRelacaoIDs( Long turmaId, Long disciplinaId, Long professorId );
	
}
