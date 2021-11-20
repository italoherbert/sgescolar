package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePessoaPaiOuMaeRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.CPFValidator;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class PessoaMaeValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private CPFValidator cpfValidator;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaMaeRequest( SavePessoaPaiOuMaeRequest request ) throws ValidacaoException {
		if ( !validatorUtil.booleanValido( request.getDesconhecido() ) )
			throw new ValidacaoException( ValidacaoErro.DESCONHECIDO_FORMATO_INVALIDO );
		
		boolean desconhecido = conversorUtil.stringParaBoolean( request.getDesconhecido() ); 
		if ( !desconhecido ) {		
			String cpf = request.getPessoa().getCpf();
			String nome = request.getPessoa().getNome();
						
			if ( cpf == null )
				throw new ValidacaoException( ValidacaoErro.CPF_MAE_OBRIGATORIO );
			if ( cpf.isBlank() )
				throw new ValidacaoException( ValidacaoErro.CPF_MAE_OBRIGATORIO );
			
			if ( nome == null )
				throw new ValidacaoException( ValidacaoErro.NOME_MAE_OBRIGATORIO );
			if ( nome.isBlank() )
				throw new ValidacaoException( ValidacaoErro.NOME_MAE_OBRIGATORIO );	
										
			if ( !validatorUtil.booleanValido( request.getFalecido() ) )
				throw new ValidacaoException( ValidacaoErro.MAE_FALECIDA_FORMATO_INVALIDO );				
			if ( !cpfValidator.cpfValido( cpf ) )
				throw new ValidacaoException( ValidacaoErro.CPF_MAE_INVALIDO, cpf );			
		}
	}
	
}
