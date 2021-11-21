package sgescolar.service.lista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.lista.admin.AdminListaEscolas;
import sgescolar.service.lista.secretario.SecretarioListaEscolas;
import sgescolar.service.lista.vasio.VasioListaEscolas;

@Component
public class ListaManager {	
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
		
	public ListaEscolas novoListaEscolas( TokenInfos tokenInfos ) {
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tokenInfos.getPerfil() );
		if ( perfil.isAdmin() )
			return new AdminListaEscolas();
		
		if ( perfil.isSecretario() ) {					
			Long logadoEID = tokenInfos.getLogadoEID();
			return new SecretarioListaEscolas( logadoEID );
		}
		
		return new VasioListaEscolas();
	}
	
}

