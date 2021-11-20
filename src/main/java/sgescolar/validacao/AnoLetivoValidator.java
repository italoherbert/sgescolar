package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class AnoLetivoValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private FeriadoValidator feriadoValidator;
	
	@Autowired
	private BimestreValidator bimestreValidator;
	
	public void validaSaveRequest( SaveAnoLetivoRequest request ) throws ValidacaoException {		
		if ( request.getAno() == null )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
		if ( request.getAno().isBlank() )
			throw new ValidacaoException( ValidacaoErro.ANO_ANO_LETIVO_OBRIGATORIO );
	
		if ( request.getPrimeiroBimestre() == null )
			throw new ValidacaoException( ValidacaoErro.PRIMEIRO_BIMESTRE_NULO );
		if ( request.getSegundoBimestre() == null )
			throw new ValidacaoException( ValidacaoErro.SEGUNDO_BIMESTRE_NULO );
		if ( request.getTerceiroBimestre() == null )
			throw new ValidacaoException( ValidacaoErro.TERCEIRO_BIMESTRE_NULO );
		if ( request.getQuartoBimestre() == null )
			throw new ValidacaoException( ValidacaoErro.QUARTO_BIMESTRE_NULO );
				
		if ( !validatorUtil.intValido( request.getAno() ) )
			throw new ValidacaoException( ValidacaoErro.ANO_LETIVO_ANO_INVALIDA );
		
		bimestreValidator.validaSaveRequest( request.getPrimeiroBimestre() );
		bimestreValidator.validaSaveRequest( request.getSegundoBimestre() );
		bimestreValidator.validaSaveRequest( request.getTerceiroBimestre() );
		bimestreValidator.validaSaveRequest( request.getQuartoBimestre() ); 
		
		List<SaveFeriadoRequest> feriados = request.getFeriados();
		if ( feriados != null )
			for( SaveFeriadoRequest freq : feriados )
				feriadoValidator.validaSaveRequest( freq );
	}
		
}
