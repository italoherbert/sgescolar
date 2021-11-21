package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class EscolaValidator {
	
	public void validaSaveRequest( SaveEscolaRequest request ) throws ValidacaoException {
		if ( request.getNome() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );
		if ( request.getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );		
	}
	
	public void validaFiltroRequest( FiltraEscolasRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_ESCOLA_OBRIGATORIO );	
	}
	
}
