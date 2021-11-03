package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraAlunosRequest;
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
		
		if ( request.getPai() != null )
			if ( request.getPai().getPessoa() == null )
				throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_PAI_OBRIGATORIOS );
		
		if ( request.getMae() != null )
			if ( request.getMae().getPessoa() == null )
				throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_MAE_OBRIGATORIOS );
	}
			
	public void validaFiltroRequest( FiltraAlunosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
