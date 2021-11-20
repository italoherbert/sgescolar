package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePessoaPaiOuMaeRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.CPFValidator;
import sgescolar.util.ValidatorUtil;

@Component
public class PessoaPaiOuMaeValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private CPFValidator cpfValidator;
		
	public void validaPaiOuMaeRequest( SavePessoaPaiOuMaeRequest request ) throws ValidacaoException {			
		String cpf = request.getPessoa().getCpf();
		String nome = request.getPessoa().getNome();
					
		if ( cpf == null )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		if ( cpf.isBlank() )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		
		if ( nome == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( nome.isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
									
		if ( !validatorUtil.booleanValido( request.getFalecido() ) )
			throw new ValidacaoException( ValidacaoErro.FALECIDO_FORMATO_INVALIDO );		
	
		if ( !cpfValidator.cpfValido( cpf ) )
			throw new ValidacaoException( ValidacaoErro.CPF_INVALIDO, cpf );					
	}
	
}

