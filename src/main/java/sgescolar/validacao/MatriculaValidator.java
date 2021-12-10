package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.filtro.FiltraMatriculaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class MatriculaValidator {
		
	public void validaBuscaRequest( FiltraMatriculaRequest request ) throws ValidacaoException {		
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );						
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );				
	}
	
}
