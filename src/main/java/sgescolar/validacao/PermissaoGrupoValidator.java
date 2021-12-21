package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class PermissaoGrupoValidator {
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SavePermissaoGrupoRequest request ) throws ValidacaoException {
		if ( !validatorUtil.booleanValido( request.getLeitura() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_LEITURA_INVALIDA );
		if ( !validatorUtil.booleanValido( request.getEscrita() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_ESCRITA_INVALIDA );
		if ( !validatorUtil.booleanValido( request.getRemocao() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_REMOCAO_INVALIDA );							
	}
	
}
