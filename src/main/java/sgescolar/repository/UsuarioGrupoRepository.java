package sgescolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgescolar.model.UsuarioGrupo;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long>{
		
	@Query( "select g from UsuarioGrupo g order by (g.perfil)")
	public List<UsuarioGrupo> buscaTodos();
	
}
