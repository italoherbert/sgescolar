package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.CPFValidator;
import sgescolar.model.request.SavePessoaResponsavelRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class PessoaOutroResponsavelValidator {

	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private CPFValidator cpfValidator;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaResponsavelRequest( SavePessoaResponsavelRequest request ) throws ValidacaoException {
		if ( !validatorUtil.booleanValido( request.getRegistrar() ) )
			throw new ValidacaoException( ValidacaoErro.FLAG_REGISTRAR_RESPONSAVEL_INVALIDO );
		
		boolean registrar = conversorUtil.stringParaBoolean( request.getRegistrar() ); 
		if ( registrar ) {		
			String cpf = request.getPessoa().getCpf();
			String nome = request.getPessoa().getNome();
						
			if ( cpf == null )
				throw new ValidacaoException( ValidacaoErro.CPF_RESPONSAVEL_OBRIGATORIO );
			if ( cpf.isBlank() )
				throw new ValidacaoException( ValidacaoErro.CPF_RESPONSAVEL_OBRIGATORIO );
			
			if ( nome == null )
				throw new ValidacaoException( ValidacaoErro.NOME_RESPONSAVEL_OBRIGATORIO );
			if ( nome.isBlank() )
				throw new ValidacaoException( ValidacaoErro.NOME_RESPONSAVEL_OBRIGATORIO );	
										
			if ( !cpfValidator.cpfValido( cpf ) )
				throw new ValidacaoException( ValidacaoErro.CPF_RESPONSAVEL_INVALIDO, cpf );			
		}
	}
	
}

