package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	@Query( "select t from Turma t join t.serie s where s.id=?1 and lower(t.descricao)=lower(?2)")
	public Optional<Turma> buscaPorDescricao( Long serieId, String descricao );

	@Query( "select t from Turma t join t.serie s where s.id=?1 and lower(t.descricao) like lower(?2)")
	public List<Turma> filtra( Long serieId, String descricaoIni );
	
	@Query( "select t from Turma t join t.serie s where s.id=?1" )
	public List<Turma> lista( Long serieId );
	
}
