package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.request.FiltraUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.UsuarioGrupoRepository;
import sgescolar.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
			
	@Autowired
	private UsuarioBuilder usuarioBuilder;			
	
	public void registraUsuario( SaveUsuarioRequest request ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
		
		Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorPerfil( request.getPerfil() );
		if ( !ugOp.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
		
		UsuarioGrupo ugrupo = ugOp.get();
		Usuario u = usuarioBuilder.novoUsuario( ugrupo );
		usuarioBuilder.carregaUsuario( u, request );
		
		usuarioRepository.save( u );
	}
		
	public void alteraUsuario( Long usuarioId, SaveUsuarioRequest request ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		Usuario u = uop.get();
		
		String username = request.getUsername();		
		if ( !username.equals( u.getUsername() ) )
			if ( usuarioRepository.findByUsername( username ).isPresent() )
				throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
		
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );
	}
		
	public List<UsuarioResponse> filtraUsuarios( FiltraUsuariosRequest request ) {
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
	
	public UsuarioResponse buscaUsuario( Long usuarioId ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findById( usuarioId );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		Usuario u = uop.get();
		
		UsuarioResponse resp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( resp, u );		
		return resp;
	}
			
}
