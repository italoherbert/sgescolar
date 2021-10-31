package sgescolar.validacao;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class EscolaValidator {
	
	public void validaSaveRequest( SaveEscolaRequest request ) throws ValidacaoException {
		if ( request.getNome() == null )
			throw new ValidationException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );
		if ( request.getNome().isBlank() )
			throw new ValidationException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );		
	}
	
}
