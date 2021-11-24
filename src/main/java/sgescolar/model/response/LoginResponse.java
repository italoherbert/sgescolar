package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
	
	private Long entidadeId;
	
	private String token;
	
	private List<String> permissoes;
		
	private TipoResponse perfil;

	private UsuarioResponse usuario;

}
