package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class InstituicaoValidator {
	
	public void validaSaveRequest( SaveInstituicaoRequest request ) throws ValidacaoException {
		if ( request.getCnpj() == null )
			throw new ValidacaoException( ValidacaoErro.INSTITUICAO_CNPJ_OBRIGATORIO );
		if ( request.getCnpj().isBlank() )
			throw new ValidacaoException( ValidacaoErro.INSTITUICAO_CNPJ_OBRIGATORIO );
		
		if ( request.getRazaoSocial() == null )
			throw new ValidacaoException( ValidacaoErro.INSTITUICAO_RAZAO_SOCIAL_OBRIGATORIA );
		if ( request.getRazaoSocial().isBlank() )
			throw new ValidacaoException( ValidacaoErro.INSTITUICAO_RAZAO_SOCIAL_OBRIGATORIA );
	}
	
}
