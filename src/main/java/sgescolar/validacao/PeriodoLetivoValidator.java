package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PeriodoLetivoEnumManager;
import sgescolar.model.request.SavePeriodoLetivoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class PeriodoLetivoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private PeriodoLetivoEnumManager periodoLetivoEnumManager;
		
	public void validaSaveRequest( SavePeriodoLetivoRequest request ) throws ValidacaoException {		
		if ( request.getDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_PERIODO_LETIVO_OBRIGATORIA );
		if ( request.getDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_PERIODO_LETIVO_OBRIGATORIA );
		
		if ( request.getDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_PERIODO_LETIVO_OBRIGATORIA );
		if ( request.getDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_PERIODO_LETIVO_OBRIGATORIA );	
		
		if ( request.getLancamentoDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_LETIVO_OBRIGATORIA );
		if ( request.getLancamentoDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_LETIVO_OBRIGATORIA );	
		
		if ( request.getLancamentoDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_LETIVO_OBRIGATORIA );
		if ( request.getLancamentoDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_LETIVO_OBRIGATORIA );	
		
		if ( !validatorUtil.dataValida( request.getDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LETIVO_DATA_INICIO_INVALIDA );		
		if ( !validatorUtil.dataValida( request.getDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LETIVO_DATA_FIM_INVALIDA );
		if ( !validatorUtil.dataValida( request.getLancamentoDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LETIVO_LANCAMENTO_DATA_INICIO_INVALIDA );		
		if ( !validatorUtil.dataValida( request.getLancamentoDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LETIVO_LANCAMENTO_DATA_FIM_INVALIDA );
		
		if ( !periodoLetivoEnumManager.enumValida( request.getTipo() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LETIVO_NAO_RECONHECIDO );
	}	
	
}

