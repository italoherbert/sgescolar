package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.DisciplinaBuilder;
import sgescolar.model.Disciplina;
import sgescolar.model.Professor;
import sgescolar.model.Turma;
import sgescolar.model.request.FiltraDisciplinasRequest;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.model.response.DisciplinaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.DisciplinaRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private TurmaRepository serieRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private DisciplinaBuilder disciplinaBuilder;
	
	public void registraDisciplina( Long turmaId, Long professorId, SaveDisciplinaRequest request, TokenInfos infos ) throws ServiceException {		
		if ( disciplinaRepository.buscaPorDescricao( turmaId, request.getDescricao() ).isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE );
		
		Optional<Turma> top = serieRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Optional<Professor> pop = professorRepository.findById( professorId );
		if ( !pop.isPresent() )
			throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
		
		Professor prof = pop.get();
		Turma turma = top.get();
		Long escolaId = turma.getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		Disciplina d = disciplinaBuilder.novoDisciplina( turma, prof );
		disciplinaBuilder.carregaDisciplina( d, request );			
		disciplinaRepository.save( d );
	}
	
	public void atualizaDisciplina( Long disciplinaId, SaveDisciplinaRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Disciplina> dop = disciplinaRepository.findById( disciplinaId );
		if ( !dop.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Disciplina d = dop.get();	
		Turma t = d.getTurma();
		Long escolaId = t.getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		if ( !d.getDescricao().equalsIgnoreCase( request.getDescricao() ) )
			if ( disciplinaRepository.buscaPorDescricao( t.getId(), request.getDescricao() ).isPresent() )
				throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE ); 
		
		disciplinaBuilder.carregaDisciplina( d, request );		
		disciplinaRepository.save( d ); 
	}
	
	public List<DisciplinaResponse> filtraDisciplinas( Long turmaId, FiltraDisciplinasRequest request, TokenInfos infos ) throws ServiceException {
		Optional<Turma> top = serieRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = top.get();
		Long escolaId = turma.getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
		
		List<Disciplina> disciplinas = disciplinaRepository.filtra( turmaId, descricaoIni );
		
		List<DisciplinaResponse> lista = new ArrayList<>();
		for( Disciplina d : disciplinas ) {
			DisciplinaResponse resp = disciplinaBuilder.novoDisciplinaResponse();
			disciplinaBuilder.carregaDisciplinaResponse( resp, d );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<DisciplinaResponse> lista( Long turmaId, TokenInfos infos ) throws ServiceException {		
		Optional<Turma> top = serieRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma turma = top.get();
		Long escolaId = turma.getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		List<Disciplina> disciplinas = disciplinaRepository.lista( turmaId );
		
		List<DisciplinaResponse> lista = new ArrayList<>();
		for( Disciplina d : disciplinas ) {
			DisciplinaResponse resp = disciplinaBuilder.novoDisciplinaResponse();
			disciplinaBuilder.carregaDisciplinaResponse( resp, d );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public DisciplinaResponse buscaDisciplina( Long disciplinaId, TokenInfos infos ) throws ServiceException {		
		Optional<Disciplina> dop = disciplinaRepository.findById( disciplinaId );
		if ( !dop.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Disciplina d = dop.get();		
		Long escolaId = d.getTurma().getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		DisciplinaResponse resp = disciplinaBuilder.novoDisciplinaResponse();
		disciplinaBuilder.carregaDisciplinaResponse( resp, d );
		return resp;
	}
	
	public void removeDisciplina( Long disciplinaId, TokenInfos infos ) throws ServiceException {		
		Optional<Disciplina> dop = disciplinaRepository.findById( disciplinaId );
		if ( !dop.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Disciplina d = dop.get();		
		Long escolaId = d.getTurma().getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		disciplinaRepository.deleteById( disciplinaId );		
	}
	
}
