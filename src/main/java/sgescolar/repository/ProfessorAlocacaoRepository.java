package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.ProfessorAlocacao;

public interface ProfessorAlocacaoRepository extends JpaRepository<ProfessorAlocacao, Long>{

	@Query( "select pa from ProfessorAlocacao pa join pa.turmaDisciplina td join pa.professor p "
			+ "where td.id=?1 and p.id=?2" )
	public Optional<ProfessorAlocacao> buscaVinculo( Long turmaDisciplinaId, Long professorId );
		
	@Query( "select pa from ProfessorAlocacao pa "
				+ "join pa.turmaDisciplina td join td.turma t join t.anoLetivo al join pa.professor p "
			+ "where p.id=?1 and al.ano=?2" )
	public List<ProfessorAlocacao> buscaPorAno( Long professorId, int ano );
	
}
