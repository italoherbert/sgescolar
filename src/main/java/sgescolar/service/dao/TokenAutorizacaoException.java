package sgescolar.service.dao;

import sgescolar.service.ServiceException;

public class TokenAutorizacaoException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public TokenAutorizacaoException(String msg, String... params) {
		super( msg, params );
	}

}
