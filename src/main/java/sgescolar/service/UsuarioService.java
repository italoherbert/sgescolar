package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.exception.UsuarioJaExisteException;
import sgescolar.exception.UsuarioNaoEncontradoException;
import sgescolar.model.Usuario;
import sgescolar.model.request.BuscaUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private UsuarioBuilder usuarioBuilder;			
	
	public void registraUsuario( SaveUsuarioRequest request ) throws UsuarioJaExisteException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( uop.isPresent() )
			throw new UsuarioJaExisteException();
		
		Usuario u = usuarioBuilder.novoUsuario();
		usuarioBuilder.carregaUsuario( u, request );
		
		usuarioRepository.save( u );
	}
		
	public void alteraUsuario( Long usuarioId, SaveUsuarioRequest request ) throws UsuarioJaExisteException, UsuarioNaoEncontradoException {
		Usuario u = usuarioRepository.findById( usuarioId ).orElseThrow( UsuarioNaoEncontradoException::new );
		
		String username = request.getUsername();
		
		if ( !username.equals( u.getUsername() ) )
			if ( usuarioRepository.findByUsername( username ).isPresent() )
				throw new UsuarioJaExisteException();
		
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );
	}
		
	public List<UsuarioResponse> filtraUsuarios( BuscaUsuariosRequest request ) {
		String usernameIni = request.getUsernameIni();
		if ( usernameIni.equals( "*" ) )
			usernameIni = "";
		usernameIni += "%";
		
		List<Usuario> usuarios = usuarioRepository.buscaPorUsernameIni( usernameIni );
		
		List<UsuarioResponse> lista = new ArrayList<>();
		for( Usuario u : usuarios ) {
			UsuarioResponse resp = usuarioBuilder.novoUsuarioResponse();
			usuarioBuilder.carregaUsuarioResponse( resp, u );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public UsuarioResponse buscaUsuario( Long usuarioId ) throws UsuarioNaoEncontradoException {
		Usuario u = usuarioRepository.findById( usuarioId ).orElseThrow( UsuarioNaoEncontradoException::new );
		
		UsuarioResponse resp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( resp, u );
		
		return resp;
	}
	
	public void deletaUsuario( Long usuarioId ) throws UsuarioNaoEncontradoException {
		Optional<Usuario> uop = usuarioRepository.findById( usuarioId );
		if ( !uop.isPresent() )
			throw new UsuarioNaoEncontradoException();
		
		usuarioRepository.deleteById( usuarioId ); 
	}
		
}
