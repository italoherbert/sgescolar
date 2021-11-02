package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class UsuarioValidator {
	
	@Autowired
	private UsuarioGrupoValidator usuarioGrupoValidator;
	
	public void validaSaveRequest( SaveUsuarioRequest req ) throws ValidacaoException {
		if ( req.getUsername() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );		
		if ( req.getUsername().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );	
		
		if ( req.getPassword() == null )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );		
		if ( req.getPassword().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );
		
		usuarioGrupoValidator.validaSaveRequest( req.getPerfil() ); 
	}
	
	public void validaFiltraRequest( FiltraUsuariosRequest req ) throws ValidacaoException {
		if ( req.getUsernameIni() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );		
		if ( req.getUsernameIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );
	}
	
}
