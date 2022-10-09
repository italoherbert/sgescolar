package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveHorarioAulaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AulaValidator {
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaSaveRequest( SaveHorarioAulaRequest request ) throws ValidacaoException {
		if ( request.getSemanaDia() == null )
			throw new ValidacaoException( ValidacaoErro.AULA_SEMANA_DIA_OBRIGATORIA );
		if ( request.getSemanaDia().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AULA_SEMANA_DIA_OBRIGATORIA );		
		 
		if ( request.getNumeroAula() == null )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIO );
		if ( request.getNumeroAula().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIO );
		
		if ( !validatorUtil.intValido( request.getSemanaDia() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_SEMANA_DIA_INVALIDO );
		if ( !validatorUtil.intValido( request.getNumeroAula() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_INVALIDO );	
		
		int dia = conversorUtil.stringParaInteiro( request.getSemanaDia() );
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );
		
		if ( dia < 1 || dia > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_DIA_FORA_DA_FAIXA );
		if ( numeroAula < 1 || numeroAula > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_FORA_DA_FAIXA );
	}
		
}

