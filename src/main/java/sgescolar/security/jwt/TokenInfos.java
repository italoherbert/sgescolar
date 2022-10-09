package sgescolar.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenInfos {

	public final static Long ID_NAO_EXTRAIDO = -1L;
	
	private String username;
	
	private String perfil;
	
	private Long logadoIID = ID_NAO_EXTRAIDO;
	
	private Long logadoUID = ID_NAO_EXTRAIDO;
	
	private Long[] logadoEIDs = {};
	
	private String[] authorities = {}; 
	
}
