package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AlunoBuilder;
import sgescolar.builder.PessoaBuilder;
import sgescolar.builder.PessoaPaiOuMaeBuilder;
import sgescolar.model.Aluno;
import sgescolar.model.Pessoa;
import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.request.FiltraAlunosRequest;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.response.AlunoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.PessoaPaiOuMaeRepository;
import sgescolar.repository.PessoaRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Autowired
	private PessoaPaiOuMaeRepository pessoaPaiOuMaeRepository;
	
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
		
		boolean ehDono = alunoRepository.verificaSeDono( logadoUID );
		if ( !ehDono )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
		
	@Transactional
	public void registraAluno( SaveAlunoRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorCpf( request.getPessoa().getCpf() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );

		Aluno a = alunoBuilder.novoAluno();
		alunoBuilder.carregaAluno( a, request );
		
		if ( request.getPai() != null ) {		
			String cpf = request.getPai().getPessoa().getCpf();
			
			Optional<Pessoa> ppop = pessoaRepository.buscaPorCpf( cpf );
			if ( ppop.isPresent() ) {
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
		
		if ( request.getMae() != null ) {		
			String cpf = request.getMae().getPessoa().getCpf();
			
			Optional<Pessoa> pmop = pessoaRepository.buscaPorCpf( cpf );
			if ( pmop.isPresent() ) {
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
		
		if ( a.getPai() != null ) {
			String paiCpfAtual = a.getPai().getPessoa().getCpf();
			String paiCpfNovo = request.getPai().getPessoa().getCpf();
			
			if ( !paiCpfNovo.equalsIgnoreCase( paiCpfAtual ) )
				if ( pessoaRepository.buscaPorCpf( paiCpfNovo ).isPresent() )
					throw new ServiceException( ServiceErro.PESSOA_PAI_JA_EXISTE );
		}
		
		if ( a.getMae() != null ) {
			String maeCpfAtual = a.getMae().getPessoa().getCpf();
			String maeCpfNovo = request.getMae().getPessoa().getCpf();

			if ( !maeCpfNovo.equalsIgnoreCase( maeCpfAtual ) ) 
				if ( pessoaRepository.buscaPorCpf( maeCpfNovo ).isPresent() )
					throw new ServiceException( ServiceErro.PESSOA_MAE_JA_EXISTE ); 
		}	
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
