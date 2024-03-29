package sgescolar.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;

public class JwtRequestFilter extends OncePerRequestFilter {
	
	private JwtTokenUtil jwtTokenUtil;
	
	public JwtRequestFilter( JwtTokenUtil jwtTokenUtil ) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
		String auth = request.getHeader( "Authorization" );
		if( auth != null ) {					
			String token = jwtTokenUtil.extraiBearerToken( auth );
			try {
				if ( !jwtTokenUtil.isTokenExpirado(token) ) {
					TokenInfos tokenInfos = jwtTokenUtil.getTokenInfos( token );					
					String username = tokenInfos.getUsername();					
					String[] authorities = tokenInfos.getAuthorities();
																
					List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
					for( String grantedAuthority : authorities )						
						grantedAuthorities.add( new SimpleGrantedAuthority( grantedAuthority ) );					
									
					UsernamePasswordAuthenticationToken tokenAuth = 
							new UsernamePasswordAuthenticationToken( username, null, grantedAuthorities );
														
					SecurityContextHolder.getContext().setAuthentication( tokenAuth );
				} 
			} catch ( SignatureException | ExpiredJwtException e ) {
				String msgerr = "Você não tem permissões para acessar este recurso."; 
				try {
					throw e;
				} catch ( SignatureException ex ) {
					msgerr = "Falha na verificação da assinatura digital do token.";
				} catch ( ExpiredJwtException ex ) {
					msgerr = "Token expirado, por favor faça login novamente.";
				}
				
				String resp = "{ \"mensagem\" : \""+msgerr+"\" }";
				response.setContentType( "application/json; charset=UTF-8" );
				response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
				
				PrintWriter writer = new PrintWriter( response.getOutputStream() );
				writer.print( resp ); 
				writer.flush();		
				
				return;
			}
		}		
		doFilter( request, response, filterChain );
	}

}
