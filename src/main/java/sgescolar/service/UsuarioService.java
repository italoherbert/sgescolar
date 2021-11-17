package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.model.Usuario;
import sgescolar.model.request.FiltraUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
				
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private UsuarioBuilder usuarioBuilder;
	
	@Transactional
	public void registraUsuario( SaveUsuarioRequest request ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
										
		Usuario u = usuarioBuilder.novoUsuario();
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );
				
		usuarioDAO.salvaUsuarioGrupoMaps( u, request );	
	}

	@Transactional
	public void alteraUsuario( Long usuarioId, SaveUsuarioRequest request ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		Usuario u = uop.get();
		
		String username = request.getUsername();		
		if ( !username.equals( u.getUsername() ) )
			if ( usuarioRepository.findByUsername( username ).isPresent() )
				throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );

		usuarioDAO.validaAlteracaoPerfil( u, request ); 						
		
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );						

		usuarioDAO.salvaUsuarioGrupoMaps( u, request );	
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
	
	public void deletaUsuario( Long usuarioId ) throws ServiceException {
		boolean existe = usuarioRepository.existsById( usuarioId );
		if ( !existe )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		usuarioRepository.deleteById( usuarioId ); 
	}
		
}
