package sgescolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.HorarioAulaBuilder;
import sgescolar.model.Escola;
import sgescolar.model.HorarioAula;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveHorarioAulaRequest;
import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.model.request.filtro.FiltraHorarioAulasRequest;
import sgescolar.model.response.HorarioAulaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.HorarioAulaRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;
import sgescolar.util.ConversorUtil;
import sgescolar.util.DataUtil;

@Service
public class HorarioService {

	@Autowired
	private HorarioAulaRepository aulaRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private HorarioAulaBuilder aulaBuilder;
	
	@Autowired
	private TokenDAO tokenDAO;
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	@Autowired
	private DataUtil dataUtil;
		
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
			td.getHorarioAulas().clear();
		
		List<SaveHorarioAulaRequest> aulasRequests = request.getHorarioAulas();
		for( SaveHorarioAulaRequest areq : aulasRequests ) {
			Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( areq.getTurmaDisciplinaId() );
			if ( !tdOp.isPresent() )
				throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
			
			TurmaDisciplina td = tdOp.get();
			
			HorarioAula aula = aulaBuilder.novoAula( td );
			aulaBuilder.carregaAula( aula, areq ); 
			
			aulaRepository.save( aula );
		}
	}

	public List<HorarioAulaResponse> listaHorarioAulas( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		List<HorarioAulaResponse> resps = new ArrayList<>();
		List<HorarioAula> aulas = td.getHorarioAulas();
		for( HorarioAula a : aulas ) {
			HorarioAulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a );
			resps.add( resp );
		}		
		return resps;
	}
		
	public List<HorarioAulaResponse> filtraHorarioAulas( Long turmaDisciplinaId, FiltraHorarioAulasRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );
		
		int semanaDia = dataUtil.getSemanaDia( dataDia );

		return this.filtraPorTDisESemanaDia( turmaDisciplinaId, semanaDia, tokenInfos ); 
	}
	
	public List<HorarioAulaResponse> filtraPorTDisESemanaDia( Long turmaDisciplinaId, int semanaDia, TokenInfos tokenInfos ) throws ServiceException {
		List<HorarioAulaResponse> responses = new ArrayList<>();
		
		List<HorarioAula> aulas = aulaRepository.filtraAulasPorTDisESemanaDia( turmaDisciplinaId, semanaDia );
		for( HorarioAula a : aulas ) {
			Escola escola = a.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
			
			tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
			
			HorarioAulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a ); 
			responses.add( resp );
		}
		return responses;
	}
	
	public List<HorarioAulaResponse> filtraPorTurma( Long turmaId, TokenInfos tokenInfos ) throws ServiceException {
		List<HorarioAulaResponse> responses = new ArrayList<>();
		
		List<HorarioAula> aulas = aulaRepository.filtraAulasPorTurma( turmaId );
		for( HorarioAula a : aulas ) {
			Escola escola = a.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
			
			tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
			
			HorarioAulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a ); 
			responses.add( resp );
		}
		return responses;
	}
	
	public List<HorarioAulaResponse> filtraPorMatricula( Long matriculaId, TokenInfos tokenInfos ) throws ServiceException {
		List<HorarioAulaResponse> responses = new ArrayList<>();
		
		List<HorarioAula> aulas = aulaRepository.filtraAulasPorMatricula( matriculaId );
		for( HorarioAula a : aulas ) {
			Escola escola = a.getTurmaDisciplina().getTurma().getAnoLetivo().getEscola();
			
			tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos ); 
			
			HorarioAulaResponse resp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( resp, a ); 
			responses.add( resp );
		}
		return responses;
	}
	
}
