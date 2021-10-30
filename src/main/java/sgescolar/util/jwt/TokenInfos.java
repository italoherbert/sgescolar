package sgescolar.util.jwt;

import lombok.Setter;

import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class TokenInfos {

	public final static Long ID_NAO_EXTRAIDO = -1L;
	
	private String username;
	
	private Long logadoUID = ID_NAO_EXTRAIDO;
	
	private Long logadoEID = ID_NAO_EXTRAIDO;
	
	private String[] authorities = {}; 
	
}
