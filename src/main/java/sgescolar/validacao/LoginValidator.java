package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.LoginRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class LoginValidator {

	public void validaRequest( LoginRequest req ) throws ValidacaoException {
		if ( req.getUsername() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );		
		if ( req.getUsername().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );	
		
		if ( req.getPassword() == null )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );		
		if ( req.getPassword().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );
	}
	
}
