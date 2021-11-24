package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query( "select t from Turma t join t.serie s where s.id=?1 and lower_unaccent(t.descricao)=lower_unaccent(?2)")
	public Optional<Turma> buscaPorDescricao( Long serieId, String descricao );

	@Query( "select t from Turma t join t.serie s where s.id=?1 and lower_unaccent(t.descricao) like lower_unaccent(?2)")
	public List<Turma> filtraPorSerie( Long serieId, String descricaoIni );
	
	@Query( "select t from Turma t join t.serie s where s.id=?1" )
	public List<Turma> listaPorSerie( Long serieId );
	
	@Query( "select t from Turma t join t.anoLetivo al where al.id=?1 and lower_unaccent(t.descricao) like lower_unaccent(?2)")
	public List<Turma> filtraPorAnoLetivo( Long anoLetivo, String descricaoIni );
	
	@Query( "select t from Turma t join t.anoLetivo al where al.id=?1" )
	public List<Turma> listaPorAnoLetivo( Long anoLetivo );
	
}
