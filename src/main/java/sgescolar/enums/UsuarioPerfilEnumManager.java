package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.UsuarioPerfil;

@Component
public class UsuarioPerfilEnumManager extends AbstractEnumManager<UsuarioPerfil> { 

	@Override
	public UsuarioPerfil[] values() {
		return UsuarioPerfil.values();
	}
		
}
