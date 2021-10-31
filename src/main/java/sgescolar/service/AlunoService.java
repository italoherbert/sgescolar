package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AlunoBuilder;
import sgescolar.model.Aluno;
import sgescolar.model.Pessoa;
import sgescolar.model.request.BuscaAlunosRequest;
import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.response.AlunoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.PessoaRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Autowired
	private AlunoBuilder alunoBuilder;
	
	@Transactional
	public void registraAluno( SaveAlunoRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorNome( request.getPessoa().getNome() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		Optional<Pessoa> paiOp = pessoaRepository.buscaPorNome( request.getPai().getPessoa().getNome() );
		if ( paiOp.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_PAI_JA_EXISTE );
		
		Optional<Pessoa> maeOp = pessoaRepository.buscaPorNome( request.getPai().getPessoa().getNome() );
		if ( maeOp.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_MAE_JA_EXISTE );
		
		Aluno a = alunoBuilder.novoAluno();
		alunoBuilder.carregaAluno( a, request );
		
		alunoRepository.save( a );						
	}
	
	public void alteraAluno( Long alunoId, SaveAlunoRequest request ) throws ServiceException {		
		Optional<Aluno> aop = alunoRepository.findById( alunoId );
		if ( !aop.isPresent() )
			throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
		
		Aluno a = aop.get();
		
		String alunoNomeAtual = a.getPessoa().getNome();
		String paiNomeAtual = a.getPai().getPessoa().getNome();
		String maeNomeAtual = a.getMae().getPessoa().getNome();
		
		String alunoNomeNovo = request.getPessoa().getNome();
		String paiNomeNovo = request.getPai().getPessoa().getNome();
		String maeNomeNovo = request.getMae().getPessoa().getNome();
		
		if ( !alunoNomeNovo.equalsIgnoreCase( alunoNomeAtual ) )
			if ( pessoaRepository.buscaPorNome( alunoNomeNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
		
		if ( !paiNomeNovo.equalsIgnoreCase( paiNomeAtual ) )
			if ( pessoaRepository.buscaPorNome( paiNomeNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_PAI_JA_EXISTE );
		
		if ( !maeNomeNovo.equalsIgnoreCase( maeNomeAtual ) ) 
			if ( pessoaRepository.buscaPorNome( maeNomeNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_MAE_JA_EXISTE ); 
				
		alunoBuilder.carregaAluno( a, request );		
		alunoRepository.save( a );		
	}
	
	public List<AlunoResponse> filtraAlunos( BuscaAlunosRequest request ) {
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
