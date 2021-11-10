package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AlunoBuilder;
import sgescolar.builder.PessoaBuilder;
import sgescolar.builder.PessoaPaiOuMaeBuilder;
import sgescolar.model.Aluno;
import sgescolar.model.Pessoa;
import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.FiltraAlunosRequest;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.response.AlunoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.PessoaPaiOuMaeRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.UsuarioGrupoRepository;

@Service
public class AlunoService {
		
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Autowired
	private PessoaPaiOuMaeRepository pessoaPaiOuMaeRepository;
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	@Autowired
	private AlunoBuilder alunoBuilder;
	
	@Autowired
	private PessoaPaiOuMaeBuilder paiOuMaeBuilder;
	
	@Autowired
	private PessoaBuilder pessoaBuilder;
		
	public void verificaSeDono( Long logadoUID, Long alunoId ) throws ServiceException {
		Optional<Aluno> aop = alunoRepository.findById( alunoId );
		if ( !aop.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Aluno a = aop.get();
		Long uid = a.getUsuario().getId();
		if ( logadoUID != uid )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
				
	public void registraAluno( SaveAlunoRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );

		String perfil = request.getUsuario().getPerfil();
		
		Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorPerfil( perfil );
		if ( !ugOp.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo ugrupo = ugOp.get();
		
		Aluno a = alunoBuilder.novoAluno( ugrupo );
		alunoBuilder.carregaAluno( a, request );
		
		if ( request.getPai() != null ) {		
			String cpf = request.getPai().getPessoa().getCpf();
			boolean cpfNaoVasio = ( cpf == null ? false : cpf.isBlank() ? false : true );
			if ( cpfNaoVasio ) {			
				Optional<Pessoa> ppop = pessoaRepository.buscaPorCpf( cpf );
				if ( !ppop.isPresent() ) {
					Optional<PessoaPaiOuMae> paiOp = pessoaPaiOuMaeRepository.buscaPorCpf( cpf );
					if ( paiOp.isPresent() ) {
						PessoaPaiOuMae pai = paiOp.get();						
						paiOuMaeBuilder.carregaPessoaPaiOuMae( pai, request.getPai() );
						a.setPai( pai ); 
					}
				} else {
					Pessoa p = ppop.get();
					pessoaBuilder.carregaPessoa( p, request.getPai().getPessoa() ); 
					a.getPai().setPessoa( p );
				}
			}
		}
		
		if ( request.getMae() != null ) {	
			String cpf = request.getMae().getPessoa().getCpf();
			boolean cpfNaoVasio = ( cpf == null ? false : cpf.isBlank() ? false : true );
			if ( cpfNaoVasio ) {						
				Optional<Pessoa> pmop = pessoaRepository.buscaPorCpf( cpf );
				if ( !pmop.isPresent() ) {
					Optional<PessoaPaiOuMae> maeOp = pessoaPaiOuMaeRepository.buscaPorCpf( cpf );
					if ( maeOp.isPresent() ) {
						PessoaPaiOuMae mae = maeOp.get();						
						paiOuMaeBuilder.carregaPessoaPaiOuMae( mae, request.getMae() );
						a.setMae( mae ); 
					}
				} else {
					Pessoa p = pmop.get();
					pessoaBuilder.carregaPessoa( p, request.getMae().getPessoa() ); 
					a.getMae().setPessoa( p );
				}
			}
		}
				
		alunoRepository.save( a );						
	}
	
	public void alteraAluno( Long alunoId, SaveAlunoRequest request ) throws ServiceException {		
		Optional<Aluno> aop = alunoRepository.findById( alunoId );
		if ( !aop.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Aluno a = aop.get();
		
		String alunoCpfAtual = a.getPessoa().getCpf();		
		String alunoCpfNovo = request.getPessoa().getCpf();
		
		if ( !alunoCpfNovo.equalsIgnoreCase( alunoCpfAtual ) )
			if ( pessoaRepository.buscaPorCpf( alunoCpfNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		alunoBuilder.carregaAluno( a, request );		
		alunoRepository.save( a );		
	}
	
	public List<AlunoResponse> filtraAlunos( FiltraAlunosRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
				
		List<Aluno> alunos = alunoRepository.filtra( nomeIni );
		
		List<AlunoResponse> lista = new ArrayList<>();
		for( Aluno a : alunos ) {
			AlunoResponse resp = alunoBuilder.novoAlunoResponse();
			alunoBuilder.carregaAlunoResponse( resp, a );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	String tiraAcentos( String str ) {
		String nova = "";
		int len = str.length();
		for( int i = 0; i < len; i++ )
			nova += ( str.charAt( i ) == 'ì' ? 'i' : str.charAt( i ) );
		return nova;
	}
	
	public AlunoResponse buscaAluno( Long alunoId ) throws ServiceException {
		Optional<Aluno> aop = alunoRepository.findById( alunoId );
		if ( !aop.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Aluno a = aop.get();
		
		AlunoResponse resp = alunoBuilder.novoAlunoResponse();
		alunoBuilder.carregaAlunoResponse( resp, a );
		
		return resp;
	}
	
	public void deletaAluno( Long alunoId )  throws ServiceException {
		boolean existe = alunoRepository.existsById( alunoId );
		if ( !existe )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		alunoRepository.deleteById( alunoId ); 
	}
	
}
