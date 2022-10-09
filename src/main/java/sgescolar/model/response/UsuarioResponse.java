package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponse {

	private Long id;	
	
	private String username;
	
	private TipoResponse perfil;
			
	private List<UsuarioGrupoResponse> grupos;
		
}
