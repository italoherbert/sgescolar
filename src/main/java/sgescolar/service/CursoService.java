package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.CursoBuilder;
import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.enums.tipos.CursoModalidade;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.CursoRepository;
import sgescolar.repository.EscolaRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenDAO;

@Service
public class CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private EscolaRepository escolaRepository;
	
	@Autowired
	private CursoModalidadeEnumManager cursoModalidadeEnumManager;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private CursoBuilder cursoBuilder;
	
	public void registraCurso( Long escolaId, SaveCursoRequest request, TokenInfos infos ) throws ServiceException {		
		if ( cursoRepository.buscaPorNome( escolaId, request.getNome() ).isPresent() )
			throw new ServiceException( ServiceErro.CURSO_JA_EXISTE );
		
		Optional<Escola> escolaOp = escolaRepository.findById( escolaId );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		tokenDAO.validaEIDOuAdmin( escolaId, infos ); 
		
		Curso c = cursoBuilder.novoCurso( escolaOp.get() );
		cursoBuilder.carregaCurso( c, request );			
		cursoRepository.save( c );
	}
	
	public void atualizaCurso( Long cursoId, SaveCursoRequest request, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Long eid = c.getEscola().getId();
		
		tokenDAO.validaEIDOuAdmin( eid, infos );
		
		if ( !c.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( cursoRepository.buscaPorNome( eid, request.getNome() ).isPresent() )
				throw new ServiceException( ServiceErro.CURSO_JA_EXISTE ); 
		
		cursoBuilder.carregaCurso( c, request );		
		cursoRepository.save( c ); 
	}
	
	public List<CursoResponse> filtraCursos( Long escolaId, FiltraCursosRequest request, TokenInfos infos ) throws ServiceException {
		tokenDAO.validaEIDOuAdmin( escolaId, infos );
		
		CursoModalidade modalidade = cursoModalidadeEnumManager.getEnum( request.getModalidade() );

		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni = "%" + nomeIni + "%";
		
		List<Curso> cursos = cursoRepository.filtra( escolaId, nomeIni, modalidade );
		
		List<CursoResponse> lista = new ArrayList<>();
		for( Curso c : cursos ) {
			CursoResponse resp = new CursoResponse();
			cursoBuilder.carregaCursoResponse( resp, c );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public CursoResponse buscaCurso( Long cursoId, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola e = c.getEscola();
		
		tokenDAO.validaEIDOuAdmin( e.getId(), infos );
		
		CursoResponse resp = new CursoResponse();
		cursoBuilder.carregaCursoResponse( resp, c );
		return resp;
	}
	
	public void removeCurso( Long cursoId, TokenInfos infos ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola e = c.getEscola();
		
		tokenDAO.validaEIDOuAdmin( e.getId(), infos ); 
		
		cursoRepository.deleteById( cursoId );		
	}
			
}
