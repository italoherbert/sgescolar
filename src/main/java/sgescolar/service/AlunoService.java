package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sgescolar.builder.AlunoBuilder;
import sgescolar.builder.PessoaBuilder;
import sgescolar.builder.PessoaResponsavelBuilder;
import sgescolar.model.Aluno;
import sgescolar.model.Pessoa;
import sgescolar.model.PessoaResponsavel;
import sgescolar.model.Usuario;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.request.filtro.FiltraAlunosRequest;
import sgescolar.model.response.AlunoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.PessoaResponsavelRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.dao.PessoaDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class AlunoService {
		
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Autowired
	private PessoaResponsavelRepository pessoaResponsavelRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private AlunoBuilder alunoBuilder;
	
	@Autowired
	private PessoaResponsavelBuilder responsaelBuilder;
	
	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private PessoaDAO pessoaDAO;			

	@Transactional
	public void registraAluno( SaveAlunoRequest request ) throws ServiceException {
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
						
		Aluno a = alunoBuilder.novoAluno();
		alunoBuilder.carregaAluno( a, request );
				
		if ( request.getPai() != null ) {		
			String cpf = request.getPai().getPessoa().getCpf();
			boolean cpfNaoVasio = ( cpf == null ? false : cpf.isBlank() ? false : true );
			if ( cpfNaoVasio ) {			
				Optional<Pessoa> ppop = pessoaRepository.buscaPorCpf( cpf );
				if ( !ppop.isPresent() ) {
					Optional<PessoaResponsavel> paiOp = pessoaResponsavelRepository.buscaPorCpf( cpf );
					if ( paiOp.isPresent() ) {
						PessoaResponsavel pai = paiOp.get();						
						responsaelBuilder.carregaPessoaResponsavel( pai, request.getPai() );
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
					Optional<PessoaResponsavel> maeOp = pessoaResponsavelRepository.buscaPorCpf( cpf );
					if ( maeOp.isPresent() ) {
						PessoaResponsavel mae = maeOp.get();						
						responsaelBuilder.carregaPessoaResponsavel( mae, request.getMae() );
						a.setMae( mae ); 
					}
				} else {
					Pessoa p = pmop.get();
					pessoaBuilder.carregaPessoa( p, request.getMae().getPessoa() ); 
					a.getMae().setPessoa( p );
				}
			}
		}
		
		if ( request.getResponsavel() != null ) {	
			String cpf = request.getResponsavel().getPessoa().getCpf();
			boolean cpfNaoVasio = ( cpf == null ? false : cpf.isBlank() ? false : true );
			if ( cpfNaoVasio ) {						
				Optional<Pessoa> rop = pessoaRepository.buscaPorCpf( cpf );
				if ( !rop.isPresent() ) {
					Optional<PessoaResponsavel> rOp = pessoaResponsavelRepository.buscaPorCpf( cpf );
					if ( rOp.isPresent() ) {
						PessoaResponsavel responsavel = rOp.get();						
						responsaelBuilder.carregaPessoaResponsavel( responsavel, request.getResponsavel() );
						a.setMae( responsavel ); 
					}
				} else {
					Pessoa p = rop.get();
					pessoaBuilder.carregaPessoa( p, request.getResponsavel().getPessoa() ); 
					a.getResponsavel().setPessoa( p );
				}
			}
		}
				
		alunoRepository.save( a );
		usuarioDAO.salvaUsuarioGrupoMaps( a.getUsuario(), request.getUsuario() );		
	}
	
	@Transactional
	public void alteraAluno( Long alunoId, SaveAlunoRequest request ) throws ServiceException {		
		Optional<Aluno> aop = alunoRepository.findById( alunoId );
		if ( !aop.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Aluno a = aop.get();
						
		pessoaDAO.validaAlteracao( a.getPessoa(), request.getPessoa() );
		usuarioDAO.validaAlteracao( a.getUsuario(), request.getUsuario() ); 

		alunoBuilder.carregaAluno( a, request );		
		alunoRepository.save( a );		
		usuarioDAO.salvaUsuarioGrupoMaps( a.getUsuario(), request.getUsuario() );		
	}
	
	public List<AlunoResponse> filtraAlunos( FiltraAlunosRequest request, Pageable p ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
				
		List<Aluno> alunos = alunoRepository.filtra( nomeIni, p );
		
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
