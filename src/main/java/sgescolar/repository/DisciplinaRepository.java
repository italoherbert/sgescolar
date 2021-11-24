package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

	@Query( "select d from Disciplina d join d.serie s where s.id=?1 and lower_unaccent(d.descricao)=lower_unaccent(?2)")
	public Optional<Disciplina> buscaPorDescricao( Long serieId, String descricao );
	
	@Query( "select d from Disciplina d join d.serie s where s.id=?1" )
	public List<Disciplina> lista( Long serieId );
	
	@Query( "select d from Disciplina d join d.serie s where s.id=?1 and lower_unaccent(d.descricao) like lower_unaccent(?2)")
	public List<Disciplina> filtra( Long serieId, String descricaoIni );
	
}
