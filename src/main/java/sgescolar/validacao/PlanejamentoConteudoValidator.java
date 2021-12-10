package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePlanejamentoConteudoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class PlanejamentoConteudoValidator {
	
	public void validaSaveRequest( SavePlanejamentoConteudoRequest request ) throws ValidacaoException {
		if ( request.getConteudo() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_CONTEUDO_OBRIGATORIO );
		if ( request.getConteudo().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_CONTEUDO_OBRIGATORIO );		
	}
	
}
