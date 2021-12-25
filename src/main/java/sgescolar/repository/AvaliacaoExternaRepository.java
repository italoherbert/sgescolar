package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.AvaliacaoExterna;

public interface AvaliacaoExternaRepository extends JpaRepository<AvaliacaoExterna, Long> {
	
	@Query( "select aext "
			+ "from AvaliacaoExterna aext join aext.turmaDisciplina td join td.turma t join t.anoLetivo al "
				+ "join t.matriculas mat join mat.aluno a "
			+ "where a.id=?1 and td.id=?2 and al.ano=?3" )
	public Optional<AvaliacaoExterna> buscaAvaliacaoExternaAnoAtual( Long alunoId, Long turmaDisciplinaId, int ano );	
}
