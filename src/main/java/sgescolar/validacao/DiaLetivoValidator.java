package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveDiaLetivoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class DiaLetivoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveDiaLetivoRequest request ) throws ValidacaoException {
		if ( request.getDataDia() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_DIA_LETIVO_OBRIGATORIA );
		if ( request.getDataDia().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_DIA_LETIVO_OBRIGATORIA );
		
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );
	}
	
	
}