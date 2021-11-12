package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.UsuarioGrupoMap;

@Component
public class UsuarioGrupoMapBuilder {
			
	public UsuarioGrupoMap novoUsuarioGrupoMap( Usuario usuario, UsuarioGrupo grupo ) {
		UsuarioGrupoMap map = new UsuarioGrupoMap();
		map.setUsuario( usuario );
		map.setGrupo( grupo );	
		return map;
	}	
	
}

