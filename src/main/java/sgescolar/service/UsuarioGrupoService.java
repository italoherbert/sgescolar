package sgescolar.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.builder.UsuarioGrupoBuilder;
import sgescolar.exception.TentativaDeletarGrupoNaoVazioException;
import sgescolar.exception.UsuarioGrupoJaExisteException;
import sgescolar.exception.UsuarioGrupoNaoEncontradoException;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.BuscaUsuarioGruposRequest;
import sgescolar.model.request.SaveUsuarioGrupoRequest;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.model.response.UsuarioGrupoResponse;
import sgescolar.repository.PermissaoGrupoRepository;
import sgescolar.repository.RecursoRepository;
import sgescolar.repository.UsuarioGrupoRepository;

@Service
public class UsuarioGrupoService {

	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	@Autowired
	private PermissaoGrupoRepository permissaoGrupoRepository;
	
	@Autowired
	private UsuarioGrupoBuilder usuarioGrupoBuilder;
	
	@Autowired
	private PermissaoGrupoBuilder permissaoGrupoBuilder;
	
	public void sincronizaRecursos( Long grupoId ) throws UsuarioGrupoNaoEncontradoException {
		UsuarioGrupo g = usuarioGrupoRepository.findById( grupoId ).orElseThrow( UsuarioGrupoNaoEncontradoException::new );
		
		List<PermissaoGrupo> permissaoGrupos = g.getPermissaoGrupos();
		List<Recurso> recursos = recursoRepository.findAll();

		int size = permissaoGrupos.size();
		
		for( Recurso r : recursos ) {
			boolean achou = false;
			for( int i = 0; !achou && i < size; i++ ) {
				Recurso r2 = permissaoGrupos.get( i ).getRecurso();
				if ( r.getNome().equalsIgnoreCase( r2.getNome() ) )
					achou = true;								
			}
			
			if ( !achou ) {
				PermissaoGrupo pg = permissaoGrupoBuilder.novoINIPermissaoGrupo( g, r );
				permissaoGrupoRepository.save( pg );
			}				
		}
	}

	
	@Transactional
	public void registraGrupo( SaveUsuarioGrupoRequest request ) throws UsuarioGrupoJaExisteException {
		Optional<UsuarioGrupo> gop = usuarioGrupoRepository.buscaPorNome( request.getNome() );
		if ( gop.isPresent() )
			throw new UsuarioGrupoJaExisteException();
				
		UsuarioGrupo g = usuarioGrupoBuilder.novoUsuarioGrupo();
		usuarioGrupoBuilder.carregaUsuarioGrupo( g, request );
		
		usuarioGrupoRepository.save( g );
		
		List<Recurso> recursos = recursoRepository.findAll(); 
		for( Recurso r : recursos ) {
			PermissaoGrupo pg = permissaoGrupoBuilder.novoINIPermissaoGrupo( g, r );			
			permissaoGrupoRepository.save( pg );
		}		
	}
	
	public void alteraGrupoInfo( Long grupoId, SaveUsuarioGrupoRequest request ) 
			throws UsuarioGrupoJaExisteException, 
				UsuarioGrupoNaoEncontradoException {
		UsuarioGrupo g = usuarioGrupoRepository.findById( grupoId ).orElseThrow( UsuarioGrupoNaoEncontradoException::new );
		
		String nome = request.getNome();
		
		if ( !nome.equalsIgnoreCase( g.getNome() ) )
			if ( usuarioGrupoRepository.buscaPorNome( nome ).isPresent() )
				throw new UsuarioGrupoJaExisteException();
		
		usuarioGrupoBuilder.carregaUsuarioGrupo( g, request );		
		usuarioGrupoRepository.save( g );		
	}
	
	public List<UsuarioGrupoResponse> filtraGrupos( BuscaUsuarioGruposRequest request ) {
		String nome = request.getNomeIni();
		if ( nome.equals( "*" ) )
			nome = "";
		nome += "%";
		
		List<UsuarioGrupo> grupos = usuarioGrupoRepository.filtra( nome );
		
		List<UsuarioGrupoResponse> lista = new ArrayList<>();
		for( UsuarioGrupo g : grupos ) {
			UsuarioGrupoResponse resp = usuarioGrupoBuilder.novoUsuarioGrupoResponse();
			usuarioGrupoBuilder.carregaUsuarioGrupoResponse( resp, g );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public UsuarioGrupoResponse buscaGrupo( Long grupoId ) throws UsuarioGrupoNaoEncontradoException {
		UsuarioGrupo g = usuarioGrupoRepository.findById( grupoId ).orElseThrow( UsuarioGrupoNaoEncontradoException::new );
		
		UsuarioGrupoResponse resp = usuarioGrupoBuilder.novoUsuarioGrupoResponse();
		usuarioGrupoBuilder.carregaUsuarioGrupoResponse( resp, g );
		
		List<PermissaoGrupoResponse> permissaoGrupos = resp.getPermissaoGrupos();
		Collections.sort( permissaoGrupos, ( pg1, pg2 ) -> {
			return pg1.getRecurso().compareTo( pg2.getRecurso() );
		} );
		
		return resp;
	}
		
	public String[] listaGrupos() {	
		List<UsuarioGrupo> grupos = usuarioGrupoRepository.buscaTodos();
		String[] lista = new String[ grupos.size() ];
		for( int i = 0; i < lista.length; i++ )
			lista[ i ] = grupos.get( i ).getNome();
		
		return lista;
	}	

	public void deletaUsuarioGrupo( Long grupoId ) 
			throws UsuarioGrupoNaoEncontradoException, 
				TentativaDeletarGrupoNaoVazioException {
		
		Optional<UsuarioGrupo> gop = usuarioGrupoRepository.findById( grupoId );
		if ( !gop.isPresent() )
			throw new UsuarioGrupoNaoEncontradoException();
		
		UsuarioGrupo g = gop.get();
		if ( !g.getUsuario().isEmpty() )
			throw new TentativaDeletarGrupoNaoVazioException();
		
		usuarioGrupoRepository.deleteById( grupoId ); 
	}
	
}
