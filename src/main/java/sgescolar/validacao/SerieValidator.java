package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraSeriesRequest;
import sgescolar.model.request.SaveSerieRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class SerieValidator {
		
	public void validaSaveRequest( SaveSerieRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );						
	}
	
	public void validaFiltroRequest( FiltraSeriesRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );			
	}
	
}

