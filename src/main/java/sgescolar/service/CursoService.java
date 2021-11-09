package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.CursoBuilder;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.CursoRepository;
import sgescolar.repository.EscolaRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private EscolaRepository escolaRepository;	
	
	@Autowired
	private CursoBuilder cursoBuilder;
	
	public void registraCurso( Long logadoEID, SaveCursoRequest request ) throws ServiceException {		
		if ( cursoRepository.buscaPorNome( logadoEID, request.getNome() ).isPresent() )
			throw new ServiceException( ServiceErro.CURSO_JA_EXISTE );
		
		Optional<Escola> escolaOp = escolaRepository.findById( logadoEID );
		if ( !escolaOp.isPresent() )
			throw new ServiceException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
		
		Curso c = cursoBuilder.novoCurso( escolaOp.get() );
		cursoBuilder.carregaCurso( c, request );			
		cursoRepository.save( c );
	}
	
	public void atualizaCurso( Long logadoEID, Long cursoId, SaveCursoRequest request ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola e = c.getEscola();
		
		if ( e.getId() != logadoEID )
			throw new ServiceException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );
		
		if ( !c.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( cursoRepository.buscaPorNome( logadoEID, request.getNome() ).isPresent() )
				throw new ServiceException( ServiceErro.CURSO_JA_EXISTE ); 
		
		cursoBuilder.carregaCurso( c, request );		
		cursoRepository.save( c ); 
	}
	
	public List<CursoResponse> filtraCursos( Long logadoEID, FiltraCursosRequest request ) {
		String nomeIni = request.getNomeIni();
		
		List<Curso> cursos;
		if ( nomeIni.equals( "*" ) )
			cursos = cursoRepository.findAll();
		else cursos = cursoRepository.filtra( logadoEID, nomeIni+"%" );
		
		List<CursoResponse> lista = new ArrayList<>();
		for( Curso c : cursos ) {
			CursoResponse resp = new CursoResponse();
			cursoBuilder.carregaCursoResponse( resp, c );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public CursoResponse buscaCurso( Long logadoEID, Long cursoId ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola e = c.getEscola();
		
		if ( e.getId() != logadoEID )
			throw new ServiceException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );
		
		CursoResponse resp = new CursoResponse();
		cursoBuilder.carregaCursoResponse( resp, c );
		return resp;
	}
	
	public void removeCurso( Long logadoEID, Long cursoId ) throws ServiceException {		
		Optional<Curso> cop = cursoRepository.findById( cursoId );
		if ( !cop.isPresent() )
			throw new ServiceException( ServiceErro.CURSO_NAO_ENCONTRADO );
		
		Curso c = cop.get();		
		Escola e = c.getEscola();
		
		if ( e.getId() != logadoEID )
			throw new ServiceException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );
		
		cursoRepository.deleteById( cursoId );		
	}
	
}
