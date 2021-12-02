package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AulaBuilder;
import sgescolar.model.Aula;
import sgescolar.model.Escola;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAulaRequest;
import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.model.response.AulaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AulaRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class HorarioService {

	@Autowired
	private AulaRepository aulaRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private AulaBuilder aulaBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
		
	@Transactional
	public void salvaHorario( Long turmaId, SaveHorarioRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = turmaOp.get();

		Escola escola = turma.getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<TurmaDisciplina> turmaDisciplinas = turma.getTurmaDisciplinas();
		for( TurmaDisciplina td : turmaDisciplinas )
			td.getAulas().clear();
		
		List<SaveAulaRequest> aulasRequests = request.getAulas();
		for( SaveAulaRequest areq : aulasRequests ) {
			Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( areq.getTurmaDisciplinaId() );
			if ( !tdOp.isPresent() )
				throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
			
			TurmaDisciplina td = tdOp.get();
			
			Aula aula = aulaBuilder.novoAula( td );
			aulaBuilder.carregaAula( aula, areq ); 
			
			aulaRepository.save( aula );
		}
	}

	public List<AulaResponse> listaAulas( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<AulaResponse> resps = new ArrayList<>();
		List<Aula> aulas = td.getAulas();
		for( Aula a : aulas ) {
			AulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a );
			resps.add( resp );
		}		
		return resps;
	}

	
}
