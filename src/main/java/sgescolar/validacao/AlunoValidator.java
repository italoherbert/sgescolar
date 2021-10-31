package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AlunoValidator {

	@Autowired
	private PessoaValidator pessoaValidator;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	public void validaSaveRequest( SaveAlunoRequest request ) throws ValidacaoException {
		if ( request.getPessoa() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_OBRIGATORIOS );
		if ( request.getUsuario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_USUARIO_OBRIGATORIOS );
		
		pessoaValidator.validaSaveRequest( request.getPessoa() );
		usuarioValidator.validaSaveRequest( request.getUsuario() ); 
	}
	
}
