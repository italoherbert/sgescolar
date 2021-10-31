package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ProfessorBuilder;
import sgescolar.model.Pessoa;
import sgescolar.model.Professor;
import sgescolar.model.request.BuscaProfessoresRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.response.ProfessorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.PessoaRepository;
import sgescolar.repository.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	@Autowired
	private ProfessorBuilder professorBuilder;
	
	@Transactional
	public void registraProfessor( SaveProfessorRequest request ) throws ServiceException {		
		Optional<Pessoa> pop = pessoaRepository.buscaPorNome( request.getFuncionario().getPessoa().getNome() );
		if ( pop.isPresent() )
			throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
						
		Professor pr = professorBuilder.novoProfessor();
		professorBuilder.carregaProfessor( pr, request );
		
		professorRepository.save( pr );						
	}
	
	public void alteraProfessor( Long professorId, SaveProfessorRequest request ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		String professorNomeAtual = pr.getFuncionario().getPessoa().getNome();		
		String professorNomeNovo = request.getFuncionario().getPessoa().getNome();
		
		if ( !professorNomeNovo.equalsIgnoreCase( professorNomeAtual ) )
			if ( pessoaRepository.buscaPorNome( professorNomeNovo ).isPresent() )
				throw new ServiceException( ServiceErro.PESSOA_JA_EXISTE );
				
		professorBuilder.carregaProfessor( pr, request );		
		professorRepository.save( pr );		
	}
	
	public List<ProfessorResponse> filtraProfessors( BuscaProfessoresRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Professor> professors = professorRepository.filtra( nomeIni );
		
		List<ProfessorResponse> lista = new ArrayList<>();
		for( Professor pr : professors ) {
			ProfessorResponse resp = professorBuilder.novoProfessorResponse();
			professorBuilder.carregaProfessorResponse( resp, pr );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public ProfessorResponse buscaProfessor( Long professorId ) throws ServiceException {
		Optional<Professor> prOp = professorRepository.findById( professorId );
		if ( !prOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor pr = prOp.get();
		
		ProfessorResponse resp = professorBuilder.novoProfessorResponse();
		professorBuilder.carregaProfessorResponse( resp, pr );		
		return resp;
	}
	
	public void deletaProfessor( Long professorId )  throws ServiceException {
		boolean existe = professorRepository.existsById( professorId );
		if ( !existe )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		professorRepository.deleteById( professorId ); 
	}
	
}
