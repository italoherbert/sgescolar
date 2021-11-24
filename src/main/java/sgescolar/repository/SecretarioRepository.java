package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.Secretario;

public interface SecretarioRepository extends JpaRepository<Secretario, Long> {

	@Query( "select s from Secretario s join s.funcionario f join f.usuario u where u.id=?1" )
	public Optional<Secretario> buscaPorUID( Long usuarioId );
			
	@Query( "select s from Secretario s join s.escola e join s.funcionario f join f.pessoa p "
			+ "where e.id=?1 and lower_unaccent(p.nome) like lower_unaccent(?2)" )
	public List<Secretario> filtra( Long escolaId, String nomeIni );

	@Query( "select s from Secretario s join s.funcionario f join f.pessoa p where lower_unaccent(p.nome) like lower_unaccent(?1)" )
	public List<Secretario> filtra( String nomeIni );
	
}
