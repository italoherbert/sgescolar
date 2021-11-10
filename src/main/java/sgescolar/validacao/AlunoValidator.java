package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
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
		
		String perfil = request.getUsuario().getPerfil();
		if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ALUNO.name() ) )
			throw new ValidacaoException( ValidacaoErro.SEM_PERMISSAO_PARA_REG_POR_PERFIL );

		String cpf = request.getPessoa().getCpf();								
		String paiCpf = null;		
		
		boolean verificarCpf  = ( cpf == null ? false : cpf.isBlank() ? false : true );
		boolean verificarPaiCpf = false;
		boolean verificarMaeCpf = false;
				
		if ( request.getPai() != null ) {
			if ( request.getPai().getPessoa() == null )
				throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_PAI_OBRIGATORIOS );
			
			paiCpf = request.getPai().getPessoa().getCpf();
			verificarPaiCpf = ( paiCpf == null ? false : paiCpf.isBlank() ? false : true );
			if ( verificarCpf && verificarPaiCpf )
				if ( cpf.equals( paiCpf ) )
					throw new ValidacaoException( ValidacaoErro.CPF_ALUNO_IGUAL_A_CPF_PAI );
			
			if ( verificarPaiCpf )
				pessoaValidator.validaCpf( paiCpf );
		}
		
		if ( request.getMae() != null ) {
			if ( request.getMae().getPessoa() == null )
				throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_MAE_OBRIGATORIOS );
			
			String maeCpf = request.getMae().getPessoa().getCpf();
			
			verificarMaeCpf = ( maeCpf == null ? false : maeCpf.isBlank() ? false : true );
			if ( verificarCpf && verificarMaeCpf )
				if ( cpf.equals( maeCpf ) )
					throw new ValidacaoException( ValidacaoErro.CPF_ALUNO_IGUAL_A_CPF_MAE );
			if ( verificarPaiCpf && verificarMaeCpf )
				if ( paiCpf.equals( maeCpf ) )
					throw new ValidacaoException( ValidacaoErro.CPF_PAI_IGUAL_A_CPF_MAE );	
			
			if ( verificarMaeCpf )
				pessoaValidator.validaCpf( maeCpf );
		}
	}
			
	public void validaFiltroRequest( FiltraAlunosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
