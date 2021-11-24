package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.TurmaBuilder;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Serie;
import sgescolar.model.Turma;
import sgescolar.model.request.FiltraTurmasRequest;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.model.response.TurmaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AnoLetivoRepository;
import sgescolar.repository.SerieRepository;
import sgescolar.repository.TurmaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private SerieRepository serieRepository;
		
	@Autowired
	private AnoLetivoRepository anoLetivoRepository;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private TurmaBuilder turmaBuilder;
	
	public void registraTurma( Long serieId, Long anoLetivoId, SaveTurmaRequest request, TokenInfos infos ) throws ServiceException {		
		if ( turmaRepository.buscaPorDescricao( serieId, request.getDescricao() ).isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE );
		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Optional<AnoLetivo> alop = anoLetivoRepository.findById( anoLetivoId );
		if ( !alop.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alop.get();
		Serie serie = sop.get();
		Long escolaId = serie.getCurso().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		Turma t = turmaBuilder.novoTurma( serie, al );
		turmaBuilder.carregaTurma( t, request );			
		turmaRepository.save( t );
	}
	
	public void atualizaTurma( Long turmaId, SaveTurmaRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Turma> top = turmaRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Turma t = top.get();
		Serie s = t.getSerie();
		
		Long serieId = s.getId();
		Long escolaId = s.getCurso().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		if ( !t.getDescricao().equalsIgnoreCase( request.getDescricao() ) )
			if ( turmaRepository.buscaPorDescricao( serieId, request.getDescricao() ).isPresent() )
				throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE ); 
		
		turmaBuilder.carregaTurma( t, request );		
		turmaRepository.save( t ); 
	}
	
	public List<TurmaResponse> filtraTurmasPorSerie( Long serieId, FiltraTurmasRequest request, TokenInfos infos ) throws ServiceException {
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie s = sop.get();
		Long escolaId = s.getCurso().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
		
		List<Turma> turmas = turmaRepository.filtraPorSerie( serieId, descricaoIni );
		
		List<TurmaResponse> lista = new ArrayList<>();
		for( Turma t : turmas ) {
			TurmaResponse resp = turmaBuilder.novoTurmaResponse();
			turmaBuilder.carregaTurmaResponse( resp, t );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<TurmaResponse> filtraTurmasPorAnoLetivo( Long anoLetivoId, FiltraTurmasRequest request, TokenInfos infos ) throws ServiceException {
		Optional<AnoLetivo> alop = anoLetivoRepository.findById( anoLetivoId );
		if ( !alop.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alop.get();
		Long escolaId = al.getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
		
		List<Turma> turmas = turmaRepository.filtraPorAnoLetivo( anoLetivoId, descricaoIni );
		
		List<TurmaResponse> lista = new ArrayList<>();
		for( Turma t : turmas ) {
			TurmaResponse resp = turmaBuilder.novoTurmaResponse();
			turmaBuilder.carregaTurmaResponse( resp, t );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<TurmaResponse> listaTurmasPorSerie( Long serieId, TokenInfos infos ) throws ServiceException {		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie s = sop.get();
		Long escolaId = s.getCurso().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		List<Turma> turmas = turmaRepository.listaPorSerie( serieId );
		
		List<TurmaResponse> lista = new ArrayList<>();
		for( Turma t : turmas ) {
			TurmaResponse resp = turmaBuilder.novoTurmaResponse();
			turmaBuilder.carregaTurmaResponse( resp, t );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<TurmaResponse> listaTurmasPorAnoLetivo( Long anoLetivoId, TokenInfos infos ) throws ServiceException {		
		Optional<AnoLetivo> alop = anoLetivoRepository.findById( anoLetivoId );
		if ( !alop.isPresent() )
			throw new ServiceException( ServiceErro.ANO_LETIVO_NAO_ENCONTRADO );
		
		AnoLetivo al = alop.get();
		Long escolaId = al.getEscola().getId();
				
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		List<Turma> turmas = turmaRepository.listaPorAnoLetivo( anoLetivoId );
		
		List<TurmaResponse> lista = new ArrayList<>();
		for( Turma t : turmas ) {
			TurmaResponse resp = turmaBuilder.novoTurmaResponse();
			turmaBuilder.carregaTurmaResponse( resp, t );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public TurmaResponse buscaTurma( Long turmaId, TokenInfos infos ) throws ServiceException {		
		Optional<Turma> top = turmaRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Turma t = top.get();		
		Long escolaId = t.getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		TurmaResponse resp = turmaBuilder.novoTurmaResponse();
		turmaBuilder.carregaTurmaResponse( resp, t );
		return resp;
	}
	
	public void removeTurma( Long turmaId, TokenInfos infos ) throws ServiceException {		
		Optional<Turma> top = turmaRepository.findById( turmaId );
		if ( !top.isPresent() )
			throw new ServiceException( ServiceErro.TURMA_NAO_ENCONTRADA );
		
		Long escolaId = top.get().getAnoLetivo().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		turmaRepository.deleteById( turmaId );		
	}
	
}
