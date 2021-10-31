package sgescolar.security.jwt;

import sgescolar.msg.SistemaException;

public class JwtInfoException extends SistemaException {

	private static final long serialVersionUID = 1L;

	public JwtInfoException( String msg ) {
		super( msg );
	}
	
}
