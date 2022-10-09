package sgescolar.validacao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class FeriadoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
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
		
		Date dataInicio = conversorUtil.stringParaData( request.getDataInicio() );
		Date dataFim = conversorUtil.stringParaData( request.getDataFim() );
		if ( dataInicio.after( dataFim ) )
			throw new ValidacaoException( ValidacaoErro.FERIADO_DATA_INI_APOS_DATA_FIM );
	}
	
	
}
