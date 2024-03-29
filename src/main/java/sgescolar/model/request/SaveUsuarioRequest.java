package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveUsuarioRequest {
	
	private String username;
	
	private String password;
	
	private String perfil;
	
	private String[] grupos;
		
}
