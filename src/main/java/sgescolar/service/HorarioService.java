package sgescolar.service;

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
	
}
