package sgescolar.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	@Query( "select a from Avaliacao a join a.turmaDisciplina td where td.id=?1" )
	public List<Avaliacao> listaPorTurmaDisciplina( Long turmaDisciplinaId );
	
	@Query( "select a from Avaliacao a join a.turmaDisciplina td "
			+ "where td.id=?1 and a.dataAgendamento>=?2" )
	public List<Avaliacao> listaNaoRealizadasPorTurmaDisciplina( Long turmaDisciplinaId, Date dataAtual );
	
}
