package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.model.response.UsuarioGrupoResponse;

@Component
public class UsuarioGrupoBuilder {
		
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
		
	public void carregaUsuarioGrupo( UsuarioGrupo g, String perfil ) {		
		g.setPerfil( usuarioPerfilEnumManager.getEnum( perfil ) );			
	}
	
	public void carregaUsuarioGrupoResponse( UsuarioGrupoResponse resp, UsuarioGrupo g ) {
		resp.setId( g.getId() );
		resp.setPerfil( usuarioPerfilEnumManager.getString( g.getPerfil() ) ); 
		
		List<PermissaoGrupoResponse> grupos = new ArrayList<>();
		List<PermissaoGrupo> permissaoGrupos = g.getPermissaoGrupos();
		for( PermissaoGrupo pg : permissaoGrupos ) {			
			PermissaoGrupoResponse grupo = permissaoGrupoBuilder.novoPermissaoGrupoResponse();
			permissaoGrupoBuilder.carregaPermissaoGrupoResponse( grupo, pg );
			
			grupos.add( grupo );
		}
						
		resp.setPermissaoGrupos( grupos );
	}
		
	public UsuarioGrupoResponse novoUsuarioGrupoResponse() {
		return new UsuarioGrupoResponse();
	}
	
	public UsuarioGrupo novoUsuarioGrupo() {
		return new UsuarioGrupo();
	}
	
}
