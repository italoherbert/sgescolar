package sgescolar.service.filtro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.filtro.admin.AdminFiltroSecretarios;
import sgescolar.service.filtro.secretario.SecretarioFiltroSecretarios;
import sgescolar.service.filtro.vasio.VasioFiltroSecretarios;

@Component
public class FiltroManager {	
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public FiltroSecretarios novoFiltroSecretarios( TokenInfos tokenInfos ) {
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tokenInfos.getPerfil() );
		switch( perfil ) {
			case ADMIN:
				Long logadoEID = tokenInfos.getLogadoEID();
				return new SecretarioFiltroSecretarios( logadoEID );
			case SECRETARIO:
				return new AdminFiltroSecretarios();				
			default:
				return new VasioFiltroSecretarios();
		}
	}
	
}
