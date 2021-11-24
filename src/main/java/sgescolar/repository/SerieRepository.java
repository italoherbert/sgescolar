package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

	@Query( "select s from Serie s join s.curso c where c.id=?1 and lower_unaccent(s.descricao)=lower_unaccent(?2)")
	public Optional<Serie> buscaPorDescricao( Long cursoId, String descricao );
	
	@Query( "select s from Serie s join s.curso c where c.id=?1 and lower_unaccent(s.descricao) like lower_unaccent(?2)")
	public List<Serie> filtra( Long cursoId, String descricaoIni );
	
	@Query( "select s from Serie s join s.curso c where c.id=?1" )
	public List<Serie> lista( Long cursoId );
	
}
