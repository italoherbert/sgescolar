package sgescolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.HorarioAulaBuilder;
import sgescolar.builder.HorarioBuilder;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.logica.util.DataUtil;
import sgescolar.model.Escola;
import sgescolar.model.HorarioAula;
import sgescolar.model.Matricula;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveHorarioAulaRequest;
import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.model.request.filtro.FiltraHorarioAulasRequest;
import sgescolar.model.response.HorarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.HorarioAulaRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.AnoAtualDAO;
import sgescolar.service.dao.TokenDAO;

@Service
public class HorarioService {

	@Autowired
	private HorarioAulaRepository horarioAulaRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
		
	@Autowired
	private HorarioBuilder horarioBuilder;
	
	@Autowired
	private HorarioAulaBuilder horarioAulaBuilder;
	
	@Autowired
	private AnoAtualDAO anoAtualDAO;
	
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
		if ( turmaDisciplinas != null ) {
			for( TurmaDisciplina td : turmaDisciplinas ) {				
				
				List<HorarioAula> aulas = new ArrayList<>( td.getHorarioAulas() );				
				for( HorarioAula aula : aulas ) {		
					if ( aula.getListaFrequencia().isEmpty() ) {
						td.getHorarioAulas().remove( aula );
					} else {						
						aula.setAtiva( false );
					}
				}
			}
		}
		
		List<SaveHorarioAulaRequest> aulasRequests = request.getHorarioAulas();
		for( SaveHorarioAulaRequest areq : aulasRequests ) {
			Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( areq.getTurmaDisciplinaId() );
			if ( !tdOp.isPresent() )
				throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
			
			TurmaDisciplina td = tdOp.get();
			
			HorarioAula aula = horarioAulaBuilder.novoAula( td );
			horarioAulaBuilder.carregaAula( aula, areq ); 
			
			horarioAulaRepository.save( aula );
		}
	}

	public HorarioResponse listaHorarioAulas( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		return horarioBuilder.geraHorarioResponse( td );
	}
		
	public HorarioResponse filtraHorarioAulas( Long turmaDisciplinaId, FiltraHorarioAulasRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Date dataDia = conversorUtil.stringParaData( request.getDataDia() );		
		int semanaDia = dataUtil.getSemanaDia( dataDia );

		return this.filtraPorTDisESemanaDia( turmaDisciplinaId, semanaDia, tokenInfos ); 
	}
	
	public HorarioResponse filtraPorTDisESemanaDia( Long turmaDisciplinaId, int semanaDia, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Escola escola = td.getTurma().getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
				
		return horarioBuilder.geraHorarioResponse( td );
	}
	
	public HorarioResponse filtraPorTurma( Long turmaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> top = turmaRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma t = top.get();
		
		Escola escola = t.getAnoLetivo().getEscola();
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );

		return horarioBuilder.geraHorarioResponse( t );
	}
	
	public HorarioResponse filtraPorMatriculaAtual( Long alunoId, TokenInfos tokenInfos ) throws ServiceException {				
		Optional<Matricula> mop = anoAtualDAO.buscaMatriculaPorAnoAtual( alunoId );
		
		if( !mop.isPresent() )
			throw new ServiceException( ServiceErro.MATRICULA_NAO_ENCONTRADA );
		
		Matricula m = mop.get();
		
		Escola escola = m.getTurma().getAnoLetivo().getEscola();			
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, tokenInfos );
		
		Turma t = m.getTurma();
								
		return horarioBuilder.geraHorarioResponse( t );
	}			
	
}
