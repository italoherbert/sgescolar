package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Usuario;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.util.HashUtil;

@Component
public class UsuarioBuilder {

	@Autowired
	private UsuarioGrupoBuilder usuarioGrupoBuilder;
	
	@Autowired
	private HashUtil hashUtil;
		
	public void carregaUsuario( Usuario u, SaveUsuarioRequest req ) {
		u.setUsername( req.getUsername() );
		u.setPassword( hashUtil.geraHash( req.getPassword() ) );
		
		usuarioGrupoBuilder.carregaUsuarioGrupo( u.getGrupo(), req.getGrupo() );
	}
	
	public void carregaUsuarioResponse( UsuarioResponse resp, Usuario u ) {
		resp.setId( u.getId() );
		resp.setUsername( u.getUsername() );
		
		usuarioGrupoBuilder.carregaUsuarioGrupoResponse( resp.getGrupo(), u.getGrupo() ); 
	}
	
	public UsuarioResponse novoUsuarioResponse() {
		UsuarioResponse resp = new UsuarioResponse();
		resp.setGrupo( usuarioGrupoBuilder.novoUsuarioGrupoResponse() );
		return resp;
	}
	
	public Usuario novoUsuario() {
		Usuario u = new Usuario();
		u.setGrupo( usuarioGrupoBuilder.novoUsuarioGrupo() );		
		return u;
	}
	
}
