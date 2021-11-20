package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class FeriadoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveFeriadoRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_FERIADO_OBRIGATORIA );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_FERIADO_OBRIGATORIA );
		
		if ( request.getDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_FERIADO_OBRIGATORIA );
		if ( request.getDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_FERIADO_OBRIGATORIA );
		
		if ( request.getDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_FERIADO_OBRIGATORIA );
		if ( request.getDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_FERIADO_OBRIGATORIA );				
		
		if ( !validatorUtil.dataValida( request.getDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.FERIADO_DATA_INICIO_INVALIDA );
		
		if ( !validatorUtil.dataValida( request.getDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.FERIADO_DATA_FIM_INVALIDA );				
	}
	
	
}
