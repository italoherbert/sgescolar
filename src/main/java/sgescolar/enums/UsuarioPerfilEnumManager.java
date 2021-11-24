package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;

@Component
public class UsuarioPerfilEnumManager extends AbstractEnumManager<UsuarioPerfil> { 

	@Override
	public UsuarioPerfil[] values() {
		return UsuarioPerfil.values();
	}

	@Override
	protected String label(UsuarioPerfil e) {
		return e.texto();
	}
		
}
