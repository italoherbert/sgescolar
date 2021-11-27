package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.ProfessorAlocacaoBuilder;
import sgescolar.model.Escola;
import sgescolar.model.Professor;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.ProfessorAlocacaoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.ProfessorAlocacaoRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class ProfessorAlocacaoService {

	@Autowired
	private ProfessorAlocacaoRepository professorAlocacaoRepository;
		
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private ProfessorAlocacaoBuilder professorAlocacaoBuilder;
			
	public void registraProfessorAlocacao( Long turmaId, Long disciplinaId, Long professorId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> turmaDisciplinaOp = turmaDisciplinaRepository.buscaPorVinculoIDs( turmaId, disciplinaId );
		if ( !turmaDisciplinaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
						
		Optional<Professor> professorOp = professorRepository.findById( professorId );
		if ( !professorOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.buscaVinculoPorRelacaoIDs( turmaId, disciplinaId, professorId );
		if ( paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_JA_EXISTE );
			
		TurmaDisciplina turmaDisciplina = turmaDisciplinaOp.get();
		Professor professor = professorOp.get();
		Escola escola = turmaDisciplina.getTurma().getAnoLetivo().getEscola();
		
		Long escolaId = escola.getId();		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos );
		
		ProfessorAlocacao aloc = professorAlocacaoBuilder.novoEInicializaProfessorAlocacao( turmaDisciplina, professor, escola );
		professorAlocacaoBuilder.carregaProfessorAlocacao( aloc );
		professorAlocacaoRepository.save( aloc );		
	}
	
	public List<ProfessorAlocacaoResponse> listaVinculos( Long professorId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Professor> professorOp = professorRepository.findById( professorId );
		if ( !professorOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
								
		Professor professor = professorOp.get();
		List<ProfessorAlocacao> alocacoes = professor.getProfessorAlocacoes();
		
		List<ProfessorAlocacaoResponse> respLista = new ArrayList<>();
		
		for( ProfessorAlocacao td : alocacoes ) {
			Long escolaId = td.getEscola().getId();
			tokenDAO.autorizaPorEscola( escolaId, tokenInfos );
			
			ProfessorAlocacaoResponse resp = professorAlocacaoBuilder.novoProfessorAlocacaoResponse();
			professorAlocacaoBuilder.carregaProfessorAlocacaoResponse( resp, td );
			respLista.add( resp );
		}
		return respLista;
	}

	public ProfessorAlocacaoResponse getProfessorAlocacao( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> profAlocOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !profAlocOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao aloc = profAlocOp.get();
		Long escolaId = aloc.getEscola().getId();
		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		ProfessorAlocacaoResponse resp = professorAlocacaoBuilder.novoProfessorAlocacaoResponse();
		professorAlocacaoBuilder.carregaProfessorAlocacaoResponse( resp, aloc );
		return resp;
	}
	
	public void deletaProfessorAlocacao( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
				
		ProfessorAlocacao pa = paOp.get();
		Long escolaId = pa.getEscola().getId();
		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		professorAlocacaoRepository.deleteById( professorAlocacaoId );
	}
	
	public void deletaProfessorAlocacao( Long turmaId, Long disciplinaId, Long professorId, TokenInfos tokenInfos ) throws ServiceException {				
		Optional<ProfessorAlocacao> profAlocOp = professorAlocacaoRepository.buscaVinculoPorRelacaoIDs( turmaId, disciplinaId, professorId );
		if ( !profAlocOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao pa = profAlocOp.get();
		Long escolaId = pa.getEscola().getId();
		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos );
										
		professorAlocacaoRepository.delete( pa );
	}
		
}
