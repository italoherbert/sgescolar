package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraSeriesRequest;
import sgescolar.model.request.SaveSerieRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class SerieValidator {
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveSerieRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );
		
		if ( request.getGrau() == null )
			throw new ValidacaoException( ValidacaoErro.GRAU_SERIE_OBRIGATORIO );
		if ( request.getGrau().isBlank() )
			throw new ValidacaoException( ValidacaoErro.GRAU_SERIE_OBRIGATORIO );
		
		if ( !validatorUtil.intValido( request.getGrau() ) )
			throw new ValidacaoException( ValidacaoErro.GRAU_SERIE_INVALIDO );
		
	}
	
	public void validaFiltroRequest( FiltraSeriesRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_SERIE_OBRIGATORIA );			
	}
	
}

