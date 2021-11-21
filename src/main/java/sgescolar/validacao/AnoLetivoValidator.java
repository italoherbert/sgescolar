package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class AnoLetivoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
		
	public void validaSaveRequest( SaveAnoLetivoRequest request ) throws ValidacaoException {		
		if ( request.getAno() == null )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
		if ( request.getAno().isBlank() )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
					
		if ( !validatorUtil.intValido( request.getAno() ) )
			throw new ValidacaoException( ValidacaoErro.ANO_LETIVO_ANO_INVALIDO );		
	}
	
	public void validaBuscaRequest( String anostr ) throws ValidacaoException {
		if ( anostr == null )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
		if ( anostr.isBlank() )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
		
		if ( !validatorUtil.intValido( anostr ) )
			throw new ValidacaoException( ValidacaoErro.ANO_LETIVO_ANO_INVALIDO );
	}
		
}
