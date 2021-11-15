package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

	private UsuarioResponse usuario;
		
	private PerfilResponse perfil;
	
	private String token;
	
	private List<String> permissoes;

}
