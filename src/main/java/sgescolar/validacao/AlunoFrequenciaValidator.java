package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.FrequenciaModalidadeEnumManager;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class AlunoFrequenciaValidator {

	@Autowired
	private FrequenciaModalidadeEnumManager frequenciaModalidadeEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
		
	public void validaSaveRequest( SaveAlunoFrequenciaRequest request ) throws ValidacaoException {				
		if ( !validatorUtil.booleanValido( request.getEstevePresente() ) )
			throw new ValidacaoException( ValidacaoErro.ALUNO_ESTEVE_PRESENTE_VALOR_INVALIDO );				
		if ( !frequenciaModalidadeEnumManager.enumValida( request.getModalidade() ) )
			throw new ValidacaoException( ValidacaoErro.FREQUENCIA_MODALIDADE_NAO_RECONHECIDA );		
	}
	
}
