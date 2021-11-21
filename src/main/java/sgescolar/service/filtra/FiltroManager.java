package sgescolar.service.filtra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.filtra.admin.AdminFiltroEscolas;
import sgescolar.service.filtra.admin.AdminFiltroSecretarios;
import sgescolar.service.filtra.secretario.SecretarioFiltroEscolas;
import sgescolar.service.filtra.secretario.SecretarioFiltroSecretarios;
import sgescolar.service.filtra.vasio.VasioFiltroEscolas;
import sgescolar.service.filtra.vasio.VasioFiltroSecretarios;

@Component
public class FiltroManager {	
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public FiltroSecretarios novoFiltroSecretarios( TokenInfos tokenInfos ) {
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tokenInfos.getPerfil() );
		if ( perfil.isAdmin() )
			return new AdminFiltroSecretarios();
		
		if ( perfil.isSecretario() ) {					
			Long logadoEID = tokenInfos.getLogadoEID();
			return new SecretarioFiltroSecretarios( logadoEID );
		}
		
		return new VasioFiltroSecretarios();
	}
	
	public FiltroEscolas novoFiltroEscolas( TokenInfos tokenInfos ) {
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tokenInfos.getPerfil() );
		if ( perfil.isAdmin() )
			return new AdminFiltroEscolas();
		
		if ( perfil.isSecretario() ) {					
			Long logadoEID = tokenInfos.getLogadoEID();
			return new SecretarioFiltroEscolas( logadoEID );
		}
		
		return new VasioFiltroEscolas();
	}
		
}