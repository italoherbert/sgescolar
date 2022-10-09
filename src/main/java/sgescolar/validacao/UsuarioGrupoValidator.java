package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveUsuarioGrupoRequest;
import sgescolar.model.request.filtro.FiltraUsuarioGruposRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class UsuarioGrupoValidator {
	
	public void validaSaveRequest( SaveUsuarioGrupoRequest request ) throws ValidacaoException {
		if ( request.getNome() == null )
			throw new ValidacaoException( ValidacaoErro.USUARIO_GRUPO_NOME_OBRIGATORIO );
		if ( request.getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USUARIO_GRUPO_NOME_OBRIGATORIO );
	}
	
	public void validaFiltroRequest( FiltraUsuarioGruposRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.USUARIO_GRUPO_NOME_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.USUARIO_GRUPO_NOME_OBRIGATORIO );	
	}
	
}
