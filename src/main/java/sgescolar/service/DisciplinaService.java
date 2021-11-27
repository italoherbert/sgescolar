package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.DisciplinaBuilder;
import sgescolar.model.Disciplina;
import sgescolar.model.Escola;
import sgescolar.model.Serie;
import sgescolar.model.request.FiltraDisciplinasRequest;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.model.response.DisciplinaResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.DisciplinaRepository;
import sgescolar.repository.SerieRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private SerieRepository serieRepository;
		
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private DisciplinaBuilder disciplinaBuilder;		
	
	public void registraDisciplina( Long serieId, SaveDisciplinaRequest request, TokenInfos infos ) throws ServiceException {		
		if ( disciplinaRepository.buscaPorDescricao( serieId, request.getDescricao() ).isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE );
		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie serie = sop.get();
		Escola escola = serie.getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos ); 
		
		Disciplina d = disciplinaBuilder.novoDisciplina( serie );
		disciplinaBuilder.carregaDisciplina( d, request );			
		disciplinaRepository.save( d );
	}
	
	public void atualizaDisciplina( Long disciplinaId, SaveDisciplinaRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Disciplina> dop = disciplinaRepository.findById( disciplinaId );
		if ( !dop.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Disciplina d = dop.get();	
		Serie t = d.getSerie();
		Escola escola = t.getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		if ( !d.getDescricao().equalsIgnoreCase( request.getDescricao() ) )
			if ( disciplinaRepository.buscaPorDescricao( t.getId(), request.getDescricao() ).isPresent() )
				throw new ServiceException( ServiceErro.DISCIPLINA_JA_EXISTE ); 
		
		disciplinaBuilder.carregaDisciplina( d, request );		
		disciplinaRepository.save( d ); 
	}
	
	public List<DisciplinaResponse> filtraDisciplinas( Long serieId, FiltraDisciplinasRequest request, TokenInfos infos ) throws ServiceException {
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie serie = sop.get();
		Escola escola = serie.getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		String descricaoIni = request.getDescricaoIni();
		if ( descricaoIni.equals( "*" ) )
			descricaoIni = "";
		descricaoIni = "%" + descricaoIni + "%";
		
		List<Disciplina> disciplinas = disciplinaRepository.filtra( serieId, descricaoIni );
		
		List<DisciplinaResponse> lista = new ArrayList<>();
		for( Disciplina d : disciplinas ) {
			DisciplinaResponse resp = disciplinaBuilder.novoDisciplinaResponse();
			disciplinaBuilder.carregaDisciplinaResponse( resp, d );			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public List<DisciplinaResponse> lista( Long serieId, TokenInfos infos ) throws ServiceException {		
		Optional<Serie> sop = serieRepository.findById( serieId );
		if ( !sop.isPresent() )
			throw new ServiceException( ServiceErro.SERIE_NAO_ENCONTRADA );
		
		Serie serie = sop.get();
		Escola escola = serie.getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos );
		
		List<Disciplina> disciplinas = disciplinaRepository.lista( serieId );
		
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
		Escola escola = d.getSerie().getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos ); 
		
		DisciplinaResponse resp = disciplinaBuilder.novoDisciplinaResponse();
		disciplinaBuilder.carregaDisciplinaResponse( resp, d );
		return resp;
	}
	
	public void removeDisciplina( Long disciplinaId, TokenInfos infos ) throws ServiceException {		
		Optional<Disciplina> dop = disciplinaRepository.findById( disciplinaId );
		if ( !dop.isPresent() )
			throw new ServiceException( ServiceErro.DISCIPLINA_NAO_ENCONTRADA );
		
		Disciplina d = dop.get();		
		Escola escola = d.getSerie().getCurso().getEscola();
		
		tokenDAO.autorizaPorEscolaOuInstituicao( escola, infos ); 
		
		disciplinaRepository.deleteById( disciplinaId );		
	}
	
}
