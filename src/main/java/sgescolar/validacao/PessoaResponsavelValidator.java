package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SavePessoaResponsavelRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class PessoaResponsavelValidator {

	@Autowired	
	private PessoaValidator pessoaValidator;
	
	public void validaResponsavelRequest( SavePessoaResponsavelRequest request ) throws ValidacaoException {
		String cpf = request.getPessoa().getCpf();
		if ( request.getPessoa().getCpf() == null )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		if ( request.getPessoa().getCpf().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CPF_OBRIGATORIO );
		
		if ( request.getPessoa().getNome() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getPessoa().getNome().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		
		pessoaValidator.validaCpf( cpf );
	}
	
}
