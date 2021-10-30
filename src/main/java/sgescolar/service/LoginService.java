package sgescolar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.exception.UsernameNaoEncontradoException;
import sgescolar.exception.UsernamePasswordNaoCorrespondemException;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Usuario;
import sgescolar.model.request.LoginRequest;
import sgescolar.model.response.LoginResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.repository.UsuarioRepository;
import sgescolar.util.HashUtil;
import sgescolar.util.jwt.JwtTokenUtil;
import sgescolar.util.jwt.TokenInfos;

@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private UsuarioBuilder usuarioBuilder;
		
	@Autowired
	private HashUtil hashUtil;
	
	@Autowired
	private JwtTokenUtil tokenUtil;				

	public LoginResponse login( LoginRequest request ) 
			throws UsernameNaoEncontradoException, 
				UsernamePasswordNaoCorrespondemException {
		
		String username = request.getUsername();
		String password = hashUtil.geraHash( request.getPassword() );
		
		Usuario u = usuarioRepository.findByUsername( username ).orElseThrow( UsernameNaoEncontradoException::new );				
		if ( !u.getPassword().equals( password ) )
			throw new UsernamePasswordNaoCorrespondemException();
		
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
		
		TokenInfos tokenInfos = new TokenInfos();
		tokenInfos.setUsername( request.getUsername() );
		tokenInfos.setAuthorities( authorities ); 
		tokenInfos.setLogadoUID( uResp.getId() ); 
		
		String token = tokenUtil.geraToken( tokenInfos );
		
		LoginResponse resp = new LoginResponse();
		resp.setUsuario( uResp );
		resp.setToken( token );
		return resp;
	}
	
	
}
