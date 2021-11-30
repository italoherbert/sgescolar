package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveAulaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class AulaValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaSaveRequest( SaveAulaRequest request ) throws ValidacaoException {
		if ( request.getDia() == null )
			throw new ValidacaoException( ValidacaoErro.AULA_DIA_OBRIGATORIA );
		if ( request.getDia().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AULA_DIA_OBRIGATORIA );		
		
		if ( request.getNumeroAula() == null )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIA );
		if ( request.getNumeroAula().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIA );
		
		if ( !validatorUtil.intValido( request.getDia() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_DIA_INVALIDO );
		if ( !validatorUtil.intValido( request.getNumeroAula() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_INVALIDO );
		
		int dia = conversorUtil.stringParaInteiro( request.getDia() );
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );
		
		if ( dia < 1 || dia > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_DIA_FORA_DA_FAIXA );
		if ( numeroAula < 1 || numeroAula > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_FORA_DA_FAIXA );
	}
		
}

