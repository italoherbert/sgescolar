package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupoMap;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.UsuarioGrupoResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.util.HashUtil;

@Component
public class UsuarioBuilder {

	@Autowired
	private UsuarioGrupoBuilder usuarioGrupoBuilder;
			
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
				
	@Autowired
	private HashUtil hashUtil;
				
	public void carregaUsuario( Usuario u, SaveUsuarioRequest req ) {		
		u.setUsername( req.getUsername() );
		u.setPassword( hashUtil.geraHash( req.getPassword() ) );
		u.setPerfil( usuarioPerfilEnumManager.getEnum( req.getPerfil() ) );				
	}
	
	public void carregaUsuarioResponse( UsuarioResponse resp, Usuario u ) {		
		resp.setId( u.getId() ); 
		resp.setUsername( u.getUsername() );
		resp.setPerfil( usuarioPerfilEnumManager.tipoResponse( u.getPerfil() ) ); 
						
		List<UsuarioGrupoMap> maps = u.getUsuarioGrupoMaps();
		for( UsuarioGrupoMap map : maps ) {
			UsuarioGrupoResponse ugResp = usuarioGrupoBuilder.novoUsuarioGrupoResponse();
			usuarioGrupoBuilder.carregaUsuarioGrupoResponse( ugResp, map.getGrupo() );
			resp.getGrupos().add( ugResp );
		}
	}		
		
	public Usuario novoUsuario() {
		return new Usuario();	
	}	

	public UsuarioResponse novoUsuarioResponse() {
		UsuarioResponse resp = new UsuarioResponse();
		resp.setGrupos( new ArrayList<>() );
		return resp;
	}
	
}
