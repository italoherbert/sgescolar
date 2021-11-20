package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveBimestreRequest;
import sgescolar.model.request.SaveDiaLetivoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class BimestreValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private DiaLetivoValidator diaLetivoValidator;
	
	public void validaSaveRequest( SaveBimestreRequest request ) throws ValidacaoException {		
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
		
		List<SaveDiaLetivoRequest> diasLetivos = request.getDiasLetivos();
		for( SaveDiaLetivoRequest dlreq : diasLetivos )
			diaLetivoValidator.validaSaveRequest( dlreq );
	}
	
	
}

