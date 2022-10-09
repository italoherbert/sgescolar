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
import sgescolar.service.dao.TokenAutorizacaoException;
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
			
	public void registraProfessorAlocacao( Long turmaDisciplinaId, Long professorId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> turmaDisciplinaOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !turmaDisciplinaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
						
		Optional<Professor> professorOp = professorRepository.findById( professorId );
		if ( !professorOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.buscaVinculo( turmaDisciplinaId, professorId );
		if ( paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_JA_EXISTE );
			
		TurmaDisciplina turmaDisciplina = turmaDisciplinaOp.get();
		Professor professor = professorOp.get();
		Escola escola = turmaDisciplina.getTurma().getAnoLetivo().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		ProfessorAlocacao aloc = professorAlocacaoBuilder.novoProfessorAlocacao( turmaDisciplina, professor, escola );
		professorAlocacaoBuilder.carregaProfessorAlocacao( aloc );
		professorAlocacaoRepository.save( aloc );		
	}
	
	public List<ProfessorAlocacaoResponse> listaAlocacoesPorProfessor( Long professorId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Professor> professorOp = professorRepository.findById( professorId );
		if ( !professorOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
								
		Professor professor = professorOp.get();
		List<ProfessorAlocacao> alocacoes = professor.getProfessorAlocacoes();
		
		return this.listaAlocacoes( alocacoes, tokenInfos );
	}
		
	private List<ProfessorAlocacaoResponse> listaAlocacoes( List<ProfessorAlocacao> alocacoes, TokenInfos tokenInfos ) throws ServiceException {				
		List<ProfessorAlocacaoResponse> respLista = new ArrayList<>();
		
		for( ProfessorAlocacao prAloc : alocacoes ) {						
			try {
				Escola escola = prAloc.getEscola();				
				tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
				
				ProfessorAlocacaoResponse resp = professorAlocacaoBuilder.novoProfessorAlocacaoResponse();
				professorAlocacaoBuilder.carregaProfessorAlocacaoResponse( resp, prAloc );
				respLista.add( resp );
			} catch( TokenAutorizacaoException e ) {
				
			}
		}
		
		return respLista;
	}
	
	public ProfessorAlocacaoResponse getProfessorAlocacao( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> profAlocOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !profAlocOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
		
		ProfessorAlocacao aloc = profAlocOp.get();
		Escola escola = aloc.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
		
		ProfessorAlocacaoResponse resp = professorAlocacaoBuilder.novoProfessorAlocacaoResponse();
		professorAlocacaoBuilder.carregaProfessorAlocacaoResponse( resp, aloc );
		return resp;
	}
	
	public ProfessorAlocacaoResponse buscaProfessorAlocacaoPorTurmaDisciplina( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();		
		ProfessorAlocacao aloc = td.getProfessorAlocacao();
		if ( aloc == null )
			throw new ServiceException( ServiceErro.NENHUM_PROFESSOR_ALOCADO_A_TURMA_DISCIPLINA );
				
		Escola escola = aloc.getEscola();		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
		
		ProfessorAlocacaoResponse resp = professorAlocacaoBuilder.novoProfessorAlocacaoResponse();
		professorAlocacaoBuilder.carregaProfessorAlocacaoResponse( resp, aloc );
		return resp;
	}
	
	public void deletaProfessorAlocacao( Long professorAlocacaoId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<ProfessorAlocacao> paOp = professorAlocacaoRepository.findById( professorAlocacaoId );
		if ( !paOp.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_ALOCACAO_NAO_ENCONTRADA );
				
		ProfessorAlocacao pa = paOp.get();
		Escola escola = pa.getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
		
		professorAlocacaoRepository.deleteById( professorAlocacaoId );
	}
					
}
