package sgescolar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.UsuarioGrupo;
import sgescolar.repository.filtro.UsuarioGrupoFiltroRepository;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long>, UsuarioGrupoFiltroRepository {
		
	@Query( "select g from UsuarioGrupo g order by (g.nome)")
	public List<UsuarioGrupo> buscaTodos();
	
	@Query( "select g from UsuarioGrupo g where lower(g.nome)=lower(?1)")
	public Optional<UsuarioGrupo> buscaPorNome( String nome );	
	
}
