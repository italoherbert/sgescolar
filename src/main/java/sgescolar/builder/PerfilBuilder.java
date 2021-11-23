package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.response.PerfilResponse;

@Component
public class PerfilBuilder {
		
	public void carregaPerfilResponse( PerfilResponse resp, UsuarioPerfil perfil ) {
		resp.setName( perfil.name() );
		resp.setTexto( perfil.texto() ); 
	}
	
	public PerfilResponse novoPerfilResponse() {
		return new PerfilResponse();
	}
	
}

