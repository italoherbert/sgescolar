package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.UsuarioGrupo;

public interface UsuarioGrupoFiltroRepository {

	public List<UsuarioGrupo> filtra( String nomeIni );
	
}
