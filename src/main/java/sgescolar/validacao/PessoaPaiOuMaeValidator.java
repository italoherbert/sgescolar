package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePessoaPaiOuMaeRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class PessoaPaiOuMaeValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaPaiOuMaeRequest( SavePessoaPaiOuMaeRequest request ) throws ValidacaoException {
		if ( request.getPessoa().getCpf() == null )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		if ( request.getPessoa().getCpf().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		
		if ( request.getPessoa().getNome() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getPessoa().getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );			

		validatorUtil.booleanValido( request.getFalecido() );
	}
	
}
