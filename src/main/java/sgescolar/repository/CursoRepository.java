package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.enums.tipos.CursoModalidade;
import sgescolar.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> { 
	
	@Query( "select c from Curso c join c.escola e where e.id=?1 and lower_unaccent(c.descricao)=lower_unaccent(?2)" )
	public Optional<Curso> buscaPorDescricao( Long escolaId, String nome );
		
	@Query( "select c from Curso c join c.escola e where e.id=?1" )
	public List<Curso> lista( Long escolaId );
	
	@Query( "select c from Curso c join c.escola e "
			+ "where e.id=?1 and "
				+ "lower_unaccent(c.descricao) like lower_unaccent(?2) and "
				+ "(c.modalidade=?3 or ?3 is null)" )	
	public List<Curso> filtra( Long escolaId, String nomeIni, CursoModalidade modalidade );
	
}
