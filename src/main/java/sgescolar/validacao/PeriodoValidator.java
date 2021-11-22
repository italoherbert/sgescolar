package sgescolar.validacao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PeriodoEnumManager;
import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class PeriodoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private PeriodoEnumManager periodoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void validaSaveRequest( SavePeriodoRequest request ) throws ValidacaoException {		
		if ( request.getDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_PERIODO_OBRIGATORIA );
		if ( request.getDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_INICIO_PERIODO_OBRIGATORIA );
		
		if ( request.getDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_PERIODO_OBRIGATORIA );
		if ( request.getDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_PERIODO_OBRIGATORIA );	
		
		if ( request.getLancamentoDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_OBRIGATORIA );
		if ( request.getLancamentoDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_OBRIGATORIA );	
		
		if ( request.getLancamentoDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_OBRIGATORIA );
		if ( request.getLancamentoDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DATA_FIM_LANCAMENTO_PERIODO_OBRIGATORIA );	
		
		if ( !validatorUtil.dataValida( request.getDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_DATA_INICIO_INVALIDA );		
		if ( !validatorUtil.dataValida( request.getDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_DATA_FIM_INVALIDA );
		if ( !validatorUtil.dataValida( request.getLancamentoDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LANCAMENTO_DATA_INICIO_INVALIDA );		
		if ( !validatorUtil.dataValida( request.getLancamentoDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LANCAMENTO_DATA_FIM_INVALIDA );
		
		Date dataInicio = conversorUtil.stringParaData( request.getDataInicio() );
		Date dataFim = conversorUtil.stringParaData( request.getDataFim() );
		if ( dataInicio.after( dataFim ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_DATA_INI_APOS_DATA_FIM );
		
		Date lancamentoDataInicio = conversorUtil.stringParaData( request.getDataInicio() );
		Date lancamentoDataFim = conversorUtil.stringParaData( request.getDataFim() );
		if ( lancamentoDataInicio.after( lancamentoDataFim ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_LANCAMENTO_DATA_INI_APOS_DATA_FIM );
		
		if ( !periodoEnumManager.enumValida( request.getTipo() ) )
			throw new ValidacaoException( ValidacaoErro.PERIODO_NAO_RECONHECIDO );		
	}	
	
}

