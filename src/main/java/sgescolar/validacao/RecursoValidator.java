package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.BuscaRecursosRequest;
import sgescolar.model.request.SaveRecursoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class RecursoValidator {

	public void validaSaveRequest( SaveRecursoRequest request ) throws ValidacaoException {
		if ( request.getNome() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_RECURSO_OBRIGATORIO );
		if ( request.getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_RECURSO_OBRIGATORIO );		
	}
	
	public void validaBuscaRequest( BuscaRecursosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_RECURSO_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_RECURSO_OBRIGATORIO );	
	}
	
}
