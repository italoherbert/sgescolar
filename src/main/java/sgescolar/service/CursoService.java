package sgescolar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.CursoBuilder;
import sgescolar.exception.CursoJaExisteException;
import sgescolar.exception.CursoNaoEncontradoException;
import sgescolar.exception.SemPrivilegioPorEscopoEscolaException;
import sgescolar.exception.CursoModalidadeNaoReconhecidaException;
import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CursoBuilder cursoBuilder;
	
	public void registraCurso( Long logadoEID, SaveCursoRequest request ) 
			throws CursoJaExisteException, CursoModalidadeNaoReconhecidaException {
		
		if ( cursoRepository.buscaPorNome( logadoEID, request.getNome() ).isPresent() )
			throw new CursoJaExisteException();
		
		Curso c = cursoBuilder.novoCurso();
		cursoBuilder.carregaCurso( c, request );
		
		cursoRepository.save( c );
	}
	
	public void atualizaCurso( Long logadoEID, Long cursoId, SaveCursoRequest request ) 
			throws CursoNaoEncontradoException, 
				CursoJaExisteException, 
				CursoModalidadeNaoReconhecidaException,
				SemPrivilegioPorEscopoEscolaException {
		
		Curso c = cursoRepository.findById( cursoId ).orElseThrow( CursoNaoEncontradoException::new );
		
		Escola e = c.getEscola();
		if ( e.getId() != logadoEID )
			throw new SemPrivilegioPorEscopoEscolaException();
		
		if ( !c.getNome().equalsIgnoreCase( request.getNome() ) )
			if ( cursoRepository.buscaPorNome( logadoEID, request.getNome() ).isPresent() )
				throw new CursoJaExisteException();
		
		cursoBuilder.carregaCurso( c, request );
		
		cursoRepository.save( c ); 
	}
	
	public List<CursoResponse> filtraCursos( Long logadoEID, String nomeIni ) {
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
	
	public CursoResponse buscaCurso( Long logadoEID, Long cursoId ) 
			throws CursoNaoEncontradoException, SemPrivilegioPorEscopoEscolaException {
		
		Curso c = cursoRepository.findById( cursoId ).orElseThrow( CursoNaoEncontradoException::new );
		
		Escola e = c.getEscola();
		if ( e.getId() != logadoEID )
			throw new SemPrivilegioPorEscopoEscolaException();
		
		CursoResponse resp = new CursoResponse();
		cursoBuilder.carregaCursoResponse( resp, c );
		return resp;
	}
	
	public void removeCurso( Long logadoEID, Long cursoId ) 
			throws CursoNaoEncontradoException, SemPrivilegioPorEscopoEscolaException {
		
		Curso c = cursoRepository.findById( cursoId ).orElseThrow( CursoNaoEncontradoException::new );		
		if ( c == null )
			throw new CursoNaoEncontradoException();
		
		Escola e = c.getEscola();
		if ( e.getId() != logadoEID )
			throw new SemPrivilegioPorEscopoEscolaException();
		
		cursoRepository.deleteById( cursoId );		
	}
	
}
