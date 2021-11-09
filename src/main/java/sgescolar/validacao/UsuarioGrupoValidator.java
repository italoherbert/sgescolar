package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;

@Component
public class UsuarioGrupoValidator {

	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public void validaSaveRequest( String perfil ) throws ValidacaoException {
		usuarioPerfilEnumManager.enumValida( perfil );
	}
	
}
