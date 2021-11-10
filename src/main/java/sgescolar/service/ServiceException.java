package sgescolar.service;

import sgescolar.msg.SistemaException;

public class ServiceException extends SistemaException {

	private static final long serialVersionUID = 1L;
		
	public ServiceException( String msg, String... params ) {
		super( msg, params );
	}
	
}
