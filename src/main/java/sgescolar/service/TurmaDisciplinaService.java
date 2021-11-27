package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.TurmaDisciplinaBuilder;
import sgescolar.model.Disciplina;
import sgescolar.model.Serie;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.TurmaDisciplinaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.DisciplinaRepository;
import sgescolar.repository.TurmaDisciplinaRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class TurmaDisciplinaService {
	
	@Autowired
	private TurmaDisciplinaRepository turmaDisciplinaRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private TurmaDisciplinaBuilder turmaDisciplinaBuilder;
	
	@Transactional
	public void sincronizaVinculoTurmaDisciplinas( Long turmaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = turmaOp.get();
		Serie serie = turma.getSerie();
		
		Long escolaId = turma.getAnoLetivo().getEscola().getId();		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos );
		
		List<Disciplina> disciplinas = serie.getDisciplinas();
		List<TurmaDisciplina> turmaDisciplinas = turma.getTurmaDisciplinas();
		
		int tsize = turmaDisciplinas.size();
		for( Disciplina d : disciplinas ) {			
			boolean achou = false;
			for( int i = 0; !achou && i < tsize; i++ ) {
				TurmaDisciplina td = turmaDisciplinas.get( i );
				Disciplina d2 = td.getDisciplina();
				if ( d.getDescricao().equalsIgnoreCase( d2.getDescricao() ) )
					achou = true;
			}					
			
			if ( !achou ) {								
				TurmaDisciplina turmaDisciplina = turmaDisciplinaBuilder.novoTurmaDisciplina( turma, d );
				turmaDisciplinaRepository.save( turmaDisciplina );
			}
		}
		
	}
	
	public void registraTurmaDisciplina( Long turmaId, Long disciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
				
		Optional<Disciplina> disciplinaOp = disciplinaRepository.findById( disciplinaId );
		if ( !disciplinaOp.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Optional<TurmaDisciplina> turmaDiscOp = turmaDisciplinaRepository.buscaPorVinculoIDs( turmaId, disciplinaId );
		if ( turmaDiscOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_JA_EXISTE );
		
		Disciplina disciplina = disciplinaOp.get();
		Turma turma = turmaOp.get();
		
		Long escolaId = turma.getAnoLetivo().getEscola().getId();		
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos );
		
		TurmaDisciplina turmaDisciplina = turmaDisciplinaBuilder.novoTurmaDisciplina( turma, disciplina );
		turmaDisciplinaRepository.save( turmaDisciplina );
	}
			
	public List<TurmaDisciplinaResponse> listaVinculosPorTurma( Long turmaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Turma> turmaOp = turmaRepository.findById( turmaId );
		if ( !turmaOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = turmaOp.get();
		
		Long escolaId = turma.getAnoLetivo().getEscola().getId();
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		List<TurmaDisciplinaResponse> respLista = new ArrayList<>();
		
		List<TurmaDisciplina> lista = turmaDisciplinaRepository.listaPorTurma( turmaId );
		for( TurmaDisciplina td : lista ) {
			TurmaDisciplinaResponse resp = turmaDisciplinaBuilder.novoTurmaDisciplinaResponse();
			turmaDisciplinaBuilder.carregaTurmaDisciplinaResponse( resp, td );
			respLista.add( resp );
		}
		return respLista;
	}

	public TurmaDisciplinaResponse getTurmaDisciplina( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Long escolaId = td.getTurma().getAnoLetivo().getEscola().getId();
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		TurmaDisciplinaResponse resp = turmaDisciplinaBuilder.novoTurmaDisciplinaResponse();
		turmaDisciplinaBuilder.carregaTurmaDisciplinaResponse( resp, td );
		return resp;
	}
	
	public void deletaTurmaDisciplina( Long turmaDisciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.findById( turmaDisciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Long escolaId = td.getTurma().getAnoLetivo().getEscola().getId();
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		if ( !td.getProfessorAlocacoes().isEmpty() )
			throw new ServiceException( ServiceErro.VINCULO_TURMA_DISCIPLINA_RELACIONADO_NAO_DELETADO );
		
		turmaDisciplinaRepository.deleteById( turmaDisciplinaId );
	}
	
	public void deletaTurmaDisciplina( Long turmaId, Long disciplinaId, TokenInfos tokenInfos ) throws ServiceException {
		if ( !turmaRepository.existsById( turmaId ) )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );		
		if ( !disciplinaRepository.existsById( disciplinaId ) )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );		
				
		Optional<TurmaDisciplina> tdOp = turmaDisciplinaRepository.buscaPorVinculoIDs( turmaId, disciplinaId );
		if ( !tdOp.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_DISCIPLINA_NAO_ENCONTRADA );
		
		TurmaDisciplina td = tdOp.get();
		
		Long escolaId = td.getTurma().getAnoLetivo().getEscola().getId();
		tokenDAO.autorizaPorEscola( escolaId, tokenInfos ); 
		
		if ( !td.getProfessorAlocacoes().isEmpty() )
			throw new ServiceException( ServiceErro.VINCULO_TURMA_DISCIPLINA_RELACIONADO_NAO_DELETADO );
										
		turmaDisciplinaRepository.delete( td );
	}
	
}
