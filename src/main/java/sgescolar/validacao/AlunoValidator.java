package sgescolar.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.request.filtro.FiltraAlunosRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AlunoValidator {

	@Autowired
	private PessoaValidator pessoaValidator;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private PessoaPaiValidator pessoaPaiValidator;
	
	@Autowired
	private PessoaMaeValidator pessoaMaeValidator;
	
	@Autowired
	private PessoaOutroResponsavelValidator pessoaResponsavelValidator;
	
	@Autowired
	private ConversorUtil conversorUtil;
				
	public void validaSaveRequest( SaveAlunoRequest request ) throws ValidacaoException {
		if ( request.getPessoa() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_PESSOA_OBRIGATORIOS );
		if ( request.getUsuario() == null )
			throw new ValidacaoException( ValidacaoErro.DADOS_USUARIO_OBRIGATORIOS );
		
		pessoaValidator.validaSaveRequest( request.getPessoa() );
		usuarioValidator.validaSaveRequest( request.getUsuario() );

		pessoaPaiValidator.validaPaiRequest( request.getPai() );
		pessoaMaeValidator.validaMaeRequest( request.getMae() );
		pessoaResponsavelValidator.validaResponsavelRequest( request.getResponsavel() ); 
		
		String perfil = request.getUsuario().getPerfil();
		if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ALUNO.name() ) )
			throw new ValidacaoException( ValidacaoErro.SEM_PERMISSAO_PARA_REG_POR_PERFIL );
		
		String cpf = request.getPessoa().getCpf();								
		String paiCpf = request.getPai().getPessoa().getCpf();
		String maeCpf = request.getMae().getPessoa().getCpf();
		String responsavelCpf = request.getResponsavel().getPessoa().getCpf();
		
		boolean registrarPai = conversorUtil.stringParaBoolean( request.getPai().getRegistrar() );
		boolean registrarMae = conversorUtil.stringParaBoolean( request.getMae().getRegistrar() );
		boolean registrarResponsavel = conversorUtil.stringParaBoolean( request.getResponsavel().getRegistrar() );
		
		boolean verificarCpf  = ( cpf == null ? false : cpf.isBlank() ? false : true );
		boolean verificarPaiCpf = ( registrarPai == true ? ( paiCpf == null ? false : paiCpf.isBlank() ? false : true ) : false );
		boolean verificarMaeCpf = ( registrarMae == true ? ( maeCpf == null ? false : maeCpf.isBlank() ? false : true ) : false );
		boolean verificarResponsavelCpf = ( registrarResponsavel == true ? ( responsavelCpf == null ? false : responsavelCpf.isBlank() ? false : true ) : false );
					
		List<String> cpfs = new ArrayList<>();
		if ( verificarCpf )
			cpfs.add( cpf );
		if ( verificarPaiCpf )
			cpfs.add( paiCpf );
		if ( verificarMaeCpf )
			cpfs.add( maeCpf );
		if ( verificarResponsavelCpf )
			cpfs.add( responsavelCpf );
				
		int size = cpfs.size();
		for( int i = 1; i < size; i++ ) {
			String cpf1 = cpfs.get( i );
			for( int j = 0; j < i; j++ ) {
				String cpf2 = cpfs.get( j );
				
				if ( cpf1.equals( cpf2 ) )
					throw new ValidacaoException( ValidacaoErro.CPFS_IGUAIS );					
			}
		}		
	}
			
	public void validaFiltroRequest( FiltraAlunosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_PESSOA_OBRIGATORIO );	
	}
	
}
