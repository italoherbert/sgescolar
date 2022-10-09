package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePlanejamentoObjetivoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class PlanejamentoObjetivoValidator {
	
	public void validaSaveRequest( SavePlanejamentoObjetivoRequest request ) throws ValidacaoException {
		if ( request.getObjetivo() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_OBJETIVO_OBRIGATORIO );
		if ( request.getObjetivo().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_OBJETIVO_OBRIGATORIO );		
	}
	
}