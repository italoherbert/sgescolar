package sgescolar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.ListaAlunoFrequencia;

public interface ListaAlunoFrequenciaRepository extends JpaRepository<ListaAlunoFrequencia, Long> {

	@Query( "select lst from ListaAlunoFrequencia lst join lst.horarioAula a where a.id=?1 and lst.dataDia=?2")
	public Optional<ListaAlunoFrequencia> busca( Long aulaId, Date dataDia );
	
	@Query( "select lst from ListaAlunoFrequencia lst where lst.dataDia=?1")
	public List<ListaAlunoFrequencia> listaPorData( Date dataDia );
	
	
	
}
