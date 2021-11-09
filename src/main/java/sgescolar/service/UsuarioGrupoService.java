package sgescolar.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PermissaoGrupoBuilder;
import sgescolar.builder.UsuarioGrupoBuilder;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Recurso;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.response.PermissaoGrupoResponse;
import sgescolar.model.response.UsuarioGrupoResponse;
import sgescolar.msg.ServiceErro;
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
	
	public void sincronizaRecursos( Long grupoId ) throws ServiceException {
		Optional<UsuarioGrupo> gop = usuarioGrupoRepository.findById( grupoId );
		if ( !gop.isPresent() )
			throw new ServiceException(ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo g = gop.get();
		
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
			
	public UsuarioGrupoResponse buscaGrupo( Long grupoId ) throws ServiceException {
		Optional<UsuarioGrupo> gop = usuarioGrupoRepository.findById( grupoId );
		if ( !gop.isPresent() )
			throw new ServiceException(ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo g = gop.get();
		
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
			lista[ i ] = grupos.get( i ).getPerfil().name();
		
		return lista;
	}
			
}
