package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.SerieBuilder;
import sgescolar.model.Curso;
import sgescolar.model.Serie;
import sgescolar.model.request.FiltraSeriesRequest;
import sgescolar.model.request.SaveSerieRequest;
import sgescolar.model.response.SerieResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.CursoRepository;
import sgescolar.repository.SerieRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class SerieService {
	
	@Autowired
	private SerieRepository serieRepository;

	@Autowired
	private CursoRepository cursoRepository;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private SerieBuilder serieBuilder;
		
	public void registraSerie( Long cursoId, SaveSerieRequest request, TokenInfos infos ) throws ServiceException {		
		if ( serieRepository.buscaPorDescricao( cursoId, request.getDescricao() ).isPresent() )
			throw new ServiceException( ServiceErro.SERIE_JA_EXISTE );
		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();
		Long escolaId = c.getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		Serie s = serieBuilder.novoSerie( c );
		serieBuilder.carregaSerie( s, request );			
		serieRepository.save( s );
	}
	
	public void atualizaSerie( Long serieId, SaveSerieRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie s = sop.get();		
				
		Curso c = s.getCurso();
		Long cursoId = c.getId();
		Long escolaId = c.getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		if ( !s.getDescricao().equalsIgnoreCase( request.getDescricao() ) )
			if ( serieRepository.buscaPorDescricao( cursoId, request.getDescricao() ).isPresent() )
				throw new ServiceException( ServiceErro.SERIE_JA_EXISTE ); 
		
		serieBuilder.carregaSerie( s, request );		
		serieRepository.save( s ); 
	}
	
	public List<SerieResponse> filtraSeries( Long cursoId, FiltraSeriesRequest request, TokenInfos infos ) throws ServiceException {
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Long escolaId = cop.get().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
				
		List<Serie> series = serieRepository.filtra( cursoId, descricaoIni );
		
		List<SerieResponse> lista = new ArrayList<>();
		for( Serie s : series ) {
			SerieResponse resp = serieBuilder.novoSerieResponse();
			serieBuilder.carregaSerieResponse( resp, s );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<SerieResponse> listaSeries( Long cursoId, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();
		Long escolaId = c.getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		List<Serie> series = serieRepository.lista( cursoId );
		
		List<SerieResponse> lista = new ArrayList<>();
		for( Serie s : series ) {
			SerieResponse resp = serieBuilder.novoSerieResponse();
			serieBuilder.carregaSerieResponse( resp, s );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public SerieResponse buscaSerie( Long serieId, TokenInfos infos ) throws ServiceException {		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie s = sop.get();
		Long escolaId = s.getCurso().getEscola().getId();
				
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		SerieResponse resp = serieBuilder.novoSerieResponse();
		serieBuilder.carregaSerieResponse( resp, s );
		return resp;
	}
	
	public void removeSerie( Long serieId, TokenInfos infos ) throws ServiceException {		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie s = sop.get();		
		Long escolaId = s.getCurso().getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		serieRepository.deleteById( serieId );		
	}
			
}

