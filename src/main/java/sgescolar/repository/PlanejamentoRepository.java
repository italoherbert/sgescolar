package sgescolar.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.enums.tipos.PlanejamentoTipo;
import sgescolar.model.Planejamento;

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {

	@Query( "select p from Planejamento p join p.professorAlocacao pa "
			+ "where pa.id=?1 and "
				+ "lower_unaccent(p.descricao) like lower_unaccent(?2) and "
				+ "?3 between p.dataInicio and p.dataFim" )
	public List<Planejamento> filtra( Long professorAlocacaoId, String descIni, Date intervaloData );
	
	@Query( "select p from Planejamento p join p.professorAlocacao pa where pa.id=?1")
	public List<Planejamento> lista( Long professorAlocacaoId );
	
	@Query( "select p from Planejamento p join p.professorAlocacao pa where pa.id=?1 and p.tipo=?2")
	public List<Planejamento> lista( Long professorAlocacaoId, PlanejamentoTipo tipo );
		
}
