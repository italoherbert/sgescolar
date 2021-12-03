package sgescolar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {
	
	@Query( "select a from Aula a join a.listaFrequencia lf where lf.dataDia=?1 and a.numeroAula=?2" )
	public Optional<Aula> buscaAula( Date dataDia, int numero );
	
	@Query( "select a from Aula a join a.turmaDisciplina td where td.id=?1 and a.semanaDia=?2")
	public List<Aula> filtraAulasPorTDisESemanaDia( Long turmaDisciplinaId, int semanaDia );
	
}
