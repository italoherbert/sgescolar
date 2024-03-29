package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.UsuarioGrupo;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long> {
		
	@Query( "select g from UsuarioGrupo g order by (g.nome)")
	public List<UsuarioGrupo> buscaTodos();
	
	@Query( "select g from UsuarioGrupo g where lower_unaccent(g.nome)=lower_unaccent(?1)")
	public Optional<UsuarioGrupo> buscaPorNome( String nome );	
	
	@Query( "select g from UsuarioGrupo g where lower_unaccent(g.nome) like lower_unaccent(?1)")
	public List<UsuarioGrupo> filtra( String nomeIni );
	
}
