package sgescolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.AlunoFrequencia;

public interface AlunoFrequenciaRepository extends JpaRepository<AlunoFrequencia, Long> {

	@Query( "select af from AlunoFrequencia af join af.listaFrequencia lf join af.matricula m where lf.id=?1 and m.id=?2")
	public Optional<AlunoFrequencia> buscaPorIDs( Long listaAlunoFreqId, Long matId );
	
}
