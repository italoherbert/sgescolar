package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.request.filtro.FiltraUsuariosRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class UsuarioValidator {
		
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public void validaSaveRequest( SaveUsuarioRequest req ) throws ValidacaoException {
		if ( req.getUsername() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );		
		if ( req.getUsername().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );	
		
		if ( req.getPassword() == null )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );		
		if ( req.getPassword().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PASSWORD_OBRIGATORIO );	
		
		if ( req.getPerfil() == null )
			throw new ValidacaoException( ValidacaoErro.USUARIO_PERFIL_OBRIGATORIO );		
		if ( req.getPerfil().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USUARIO_PERFIL_OBRIGATORIO );
		
		if ( !usuarioPerfilEnumManager.enumValida( req.getPerfil() ) )
			throw new ValidacaoException( ValidacaoErro.USUARIO_PERFIL_NAO_RECONHECIDO, req.getPerfil() );
		
		if ( req.getGrupos() == null )
			req.setGrupos( new String[] {} );				
	}
	
	public void validaFiltraRequest( FiltraUsuariosRequest req ) throws ValidacaoException {
		if ( req.getUsernameIni() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );		
		if ( req.getUsernameIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );
	}
	
}
