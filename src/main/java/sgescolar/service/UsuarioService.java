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
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
				
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private TokenDAO tokenDAO;
	
	@Autowired
	private UsuarioBuilder usuarioBuilder;
		
	@Transactional
	public void registraUsuario( SaveUsuarioRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );									
		
		tokenDAO.autorizaUsuarioOperacao( request, tokenInfos ); 
		
		Usuario u = usuarioBuilder.novoUsuario();
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );
				
		usuarioDAO.salvaUsuarioGrupoMaps( u, request );	
	}

	@Transactional
	public void alteraUsuario( Long usuarioId, SaveUsuarioRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findById( usuarioId );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		tokenDAO.autorizaUsuarioOperacao( request, tokenInfos );

		Usuario u = uop.get();		
		usuarioDAO.validaAlteracao( u, request ); 						
		
		usuarioBuilder.carregaUsuario( u, request );		
		usuarioRepository.save( u );						

		usuarioDAO.salvaUsuarioGrupoMaps( u, request );	
	}
		
	public List<UsuarioResponse> filtraUsuarios( FiltraUsuariosRequest request, TokenInfos tokenInfos ) {		
		String usernameIni = request.getUsernameIni();
		if ( usernameIni.equals( "*" ) )
			usernameIni = "";
		usernameIni += "%";
		
		List<Usuario> usuarios = usuarioRepository.buscaPorUsernameIni( usernameIni );
		
		List<UsuarioResponse> lista = new ArrayList<>();
		for( Usuario u : usuarios ) {		
			try {
				tokenDAO.autorizaUsuarioOperacao( u.getPerfil(), tokenInfos );
				
				UsuarioResponse resp = usuarioBuilder.novoUsuarioResponse();
				usuarioBuilder.carregaUsuarioResponse( resp, u );
				
				lista.add( resp );
			} catch ( TokenAutorizacaoException ex ) {
				
			}
		}
		
		return lista;
	}
	
	public UsuarioResponse buscaUsuario( Long usuarioId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findById( usuarioId );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		Usuario u = uop.get();
		
		tokenDAO.autorizaUsuarioOperacao( u.getPerfil(), tokenInfos );
		
		UsuarioResponse resp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( resp, u );		
		return resp;
	}
	
	public void deletaUsuario( Long usuarioId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Usuario> uop = usuarioRepository.findById( usuarioId );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
		
		Usuario u = uop.get();		
		tokenDAO.autorizaUsuarioOperacao( u.getPerfil(), tokenInfos ); 
		
		usuarioRepository.deleteById( usuarioId ); 
	}
		
}
