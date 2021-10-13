package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.builder.RecursoBuilder;
import sgescolar.exception.RecursoJaExisteException;
import sgescolar.exception.RecursoNaoEncontradoException;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.BuscaRecursosRequest;
import sgescolar.model.request.SaveRecursoRequest;
import sgescolar.model.response.RecursoResponse;
import sgescolar.repository.PermissaoGrupoRepository;
import sgescolar.repository.RecursoRepository;
import sgescolar.repository.UsuarioGrupoRepository;

@Service
public class RecursoService {

	@Autowired
	private UsuarioGrupoRepository usuarioRecursoRepository;
	
	@Autowired
	private PermissaoGrupoRepository permissaoRecursoRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private RecursoBuilder recursoBuilder;
	
	@Autowired
	private PermissaoGrupoBuilder permissaoRecursoBuilder;
		
	@Transactional
	public void registraRecurso( SaveRecursoRequest request ) throws RecursoJaExisteException {
		Optional<Recurso> rop = recursoRepository.buscaPorNome( request.getNome() );
		if ( rop.isPresent() )
			throw new RecursoJaExisteException();
		
		Recurso r = recursoBuilder.novoRecurso();
		recursoBuilder.carregaRecurso( r, request );
		
		recursoRepository.save( r );				
		
		List<UsuarioGrupo> grupos = usuarioRecursoRepository.findAll();
		for( UsuarioGrupo g : grupos ) {
			PermissaoGrupo pg = permissaoRecursoBuilder.novoINIPermissaoGrupo( g, r );
			permissaoRecursoRepository.save( pg );
		}
	}
	
	public void alteraRecurso( Long recursoId, SaveRecursoRequest request ) throws RecursoJaExisteException, RecursoNaoEncontradoException {
		Recurso r = recursoRepository.findById( recursoId ).orElseThrow( RecursoNaoEncontradoException::new );
		
		String nome = request.getNome();
		
		if ( !nome.equalsIgnoreCase( r.getNome() ) )
			if ( recursoRepository.buscaPorNome( nome ).isPresent() )
				throw new RecursoJaExisteException();
		
		recursoBuilder.carregaRecurso( r, request );		
		recursoRepository.save( r );		
	}
	
	public List<RecursoResponse> filtraRecursos( BuscaRecursosRequest request ) {
		String nomeIni = request.getNomeIni();
		if ( nomeIni.equals( "*" ) )
			nomeIni = "";
		nomeIni += "%";
		
		List<Recurso> recursos = recursoRepository.filtra( nomeIni );
		
		List<RecursoResponse> lista = new ArrayList<>();
		for( Recurso r : recursos ) {
			RecursoResponse resp = recursoBuilder.novoRecursoResponse();
			recursoBuilder.carregaRecursoResponse( resp, r );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public RecursoResponse buscaRecurso( Long recursoId ) throws RecursoNaoEncontradoException {
		Recurso r = recursoRepository.findById( recursoId ).orElseThrow( RecursoNaoEncontradoException::new );
		
		RecursoResponse resp = recursoBuilder.novoRecursoResponse();
		recursoBuilder.carregaRecursoResponse( resp, r );
		
		return resp;
	}
	
	public void deletaRecurso( Long recursoId )  throws RecursoNaoEncontradoException {
		boolean existe = recursoRepository.existsById( recursoId );
		if ( !existe )
			throw new RecursoNaoEncontradoException();
		
		recursoRepository.deleteById( recursoId ); 
	}
	
	
}
