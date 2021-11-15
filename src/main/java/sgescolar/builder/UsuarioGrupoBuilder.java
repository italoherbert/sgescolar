package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.SaveUsuarioGrupoRequest;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.model.response.UsuarioGrupoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class UsuarioGrupoBuilder {
			
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
				
	public void carregaUsuarioGrupo( UsuarioGrupo g, SaveUsuarioGrupoRequest req ) {		
		g.setNome( req.getNome() );			
	}
	
	public void carregaUsuarioGrupoResponse( UsuarioGrupoResponse resp, UsuarioGrupo g ) {
		boolean deletavel = !usuarioPerfilEnumManager.enumValida( g.getNome() );

		resp.setId( g.getId() );
		resp.setNome( g.getNome() ); 
		resp.setDeletavel( conversorUtil.booleanParaString( deletavel ) );
		
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
