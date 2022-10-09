package sgescolar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.ListaFrequencia;

public interface ListaAlunoFrequenciaRepository extends JpaRepository<ListaFrequencia, Long> {

	@Query( "select lst from ListaFrequencia lst join lst.horarioAula ha where ha.id=?1 and lst.dataDia=?2")
	public Optional<ListaFrequencia> busca( Long aulaId, Date dataDia );
	
	@Query( "select lst from ListaFrequencia lst join lst.horarioAula ha join ha.turmaDisciplina td where td.id=?1 and lst.dataDia=?2")
	public List<ListaFrequencia> listaPorTDiscEDataDia( Long turmaDisciplinaId, Date dataDia );
			
}
