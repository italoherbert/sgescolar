package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.request.FiltraAdministradoresRequest;
import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AdministradorValidator {

	@Autowired
	private UsuarioValidator usuarioValidator;
		
	public void validaSaveRequest( SaveAdministradorRequest request ) throws ValidacaoException {
		if ( request.getFuncionario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_FUNCIONARIO_OBRIGATORIOS );		
		if ( request.getFuncionario().getUsuario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_USUARIO_OBRIGATORIOS );
		
		usuarioValidator.validaSaveRequest( request.getFuncionario().getUsuario() ); 
		
		String perfil = request.getFuncionario().getUsuario().getPerfil();
		if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ADMIN.name() ) )
			throw new ValidacaoException( ValidacaoErro.SEM_PERMISSAO_PARA_REG_POR_PERFIL );
	}
	
	public void validaFiltroRequest( FiltraAdministradoresRequest request ) throws ValidacaoException {
		if ( request.getUsernameIni() == null )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );
		if ( request.getUsernameIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USERNAME_OBRIGATORIO );	
	}
	
}
