package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Secretario;
import sgescolar.model.Usuario;
import sgescolar.model.request.LoginRequest;
import sgescolar.model.response.LoginResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.SecretarioRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.util.HashUtil;

@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private UsuarioBuilder usuarioBuilder;
		
	@Autowired
	private HashUtil hashUtil;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;

	public LoginResponse login( LoginRequest request ) throws ServiceException  {		
		String username = request.getUsername();
		String password = hashUtil.geraHash( request.getPassword() );
		
		Optional<Usuario> uop = usuarioRepository.findByUsername( username );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
				
		Usuario u = uop.get();
		
		if ( !u.getPassword().equals( password ) )
			throw new ServiceException( ServiceErro.USERNAME_PASSWORD_NAO_CORRESPONDEM );
		
		UsuarioResponse uResp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( uResp, u ); 
		
		List<PermissaoGrupo> permissaoGrupos = u.getGrupo().getPermissaoGrupos();
		List<String> authoritiesLista = new ArrayList<>();
		
		int size = permissaoGrupos.size();
		for( int i = 0; i < size; i++ ) {
			PermissaoGrupo p = permissaoGrupos.get( i );
			String recurso = p.getRecurso().getNome();
			if ( p.isEscrita() )
				authoritiesLista.add( recurso + PermissaoGrupo.PREFIXO_ESCRITA );
			
			if ( p.isLeitura() )
				authoritiesLista.add( recurso + PermissaoGrupo.PREFIXO_LEITURA );
			
			if ( p.isRemocao() )
				authoritiesLista.add( recurso + PermissaoGrupo.PREFIXO_REMOCAO );
		}
		
		authoritiesLista.add( "loginREAD" );
		
		String[] authorities = authoritiesLista.toArray( new String[ authoritiesLista.size() ] );
		
		Long uid = uResp.getId();
		String perfil = uResp.getGrupo().getPerfil();
		
		TokenInfos tokenInfos = new TokenInfos();
		tokenInfos.setUsername( request.getUsername() );
		tokenInfos.setAuthorities( authorities ); 
		tokenInfos.setLogadoUID( uid );
		tokenInfos.setPerfil( perfil );
		
		UsuarioPerfil uperfil = usuarioPerfilEnumManager.getEnum( perfil );
		if ( uperfil == UsuarioPerfil.SECRETARIO ) {
			Optional<Secretario> sop = secretarioRepository.buscaPorUID( uid );
			if ( !sop.isPresent() )
				throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
			
			Long eid = sop.get().getEscola().getId();
			tokenInfos.setLogadoEID( eid );
		}
		
		String token = tokenUtil.geraToken( tokenInfos );
		
		LoginResponse resp = new LoginResponse();
		resp.setUsuario( uResp );
		resp.setToken( token );
		return resp;
	}
	
	
}
