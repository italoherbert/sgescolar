package sgescolar.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	
	private final long JWT_TOKEN_VALIDADE = 5 * 60 * 60 * 1000;
	
	@Value( "jwt.secret" )
	private String secret;
		
	public String geraToken( TokenInfos tokenInfos ) {		
		Map<String, Object> claims = new HashMap<String, Object>();
		
		String[] authorities = tokenInfos.getAuthorities();
		
		String strAuthorities = "";
		for( String authority : authorities ) {
			if ( !strAuthorities.isEmpty() )
				strAuthorities += ",";
			strAuthorities += authority;
		}
				
		String username = tokenInfos.getUsername();
		Long logadoUID = tokenInfos.getLogadoUID();
		Long logadoIID = tokenInfos.getLogadoIID();
		String logadoEIDs = null;
		
		Long[] ids = tokenInfos.getLogadoEIDs();
		if ( ids.length > 0 ) {
			logadoEIDs = "";
			for( Long id : ids )
				logadoEIDs += id+" ";
		}
				
		claims.put( "authorities", strAuthorities );
		claims.put( "logadoUID", logadoUID );
		claims.put( "logadoIID", logadoIID );
		claims.put( "logadoEIDs", logadoEIDs );
		claims.put( "perfil", tokenInfos.getPerfil() );
		
		return geraToken( claims, username );				
	}
	
	public String geraToken( Map<String, Object> claims, String subject ) {
		return Jwts.builder().setClaims( claims ).setSubject( subject ).setIssuedAt( new Date() )
				.setExpiration( new Date( System.currentTimeMillis() + JWT_TOKEN_VALIDADE ) )
				.signWith( SignatureAlgorithm.HS512, secret ).compact();
	}
	
	public String extraiBearerToken( String authorizationHeader ) {
		return authorizationHeader.replace( "Bearer ", "" );
	}
		
	public boolean validaToken( String token ) {
		if ( this.isTokenExpirado( token ) )
			return false;
		return this.getSubject( token ) != null;  
	}
	
	public boolean isTokenExpirado( String token ) {
		final Date expiration = this.getExpirationDate( token );
		return expiration.before( new Date() );
	}
	
	public Date getExpirationDate( String token ) {
		return this.getTokenClaims( token ).getExpiration();
	}
		
	public String getSubject( String token ) {
		return this.getTokenClaims( token ).getSubject();
	}
	
	public TokenInfos getBearerTokenInfos( String authHeader ) {
		String token = this.extraiBearerToken( authHeader );
		return this.getTokenInfos( token );
	}		
	
	public TokenInfos getTokenInfos( String token ) {
		TokenInfos tokenInfos = new TokenInfos();
		
		Claims claims = this.getTokenClaims( token );

		tokenInfos.setUsername( claims.getSubject() );  
		
		Object authorities = claims.get( "authorities" );
		if ( authorities != null )
			tokenInfos.setAuthorities( String.valueOf( authorities ).split( "," ) );
		
		Object logadoUID = claims.get( "logadoUID" );
		if ( logadoUID != null )
			tokenInfos.setLogadoUID( Long.parseLong( String.valueOf( logadoUID ) ) );
		else tokenInfos.setLogadoUID( TokenInfos.ID_NAO_EXTRAIDO );
		
		Object logadoIID = claims.get( "logadoIID" );
		if ( logadoIID != null )
			tokenInfos.setLogadoIID( Long.parseLong( String.valueOf( logadoIID ) ) );
		else tokenInfos.setLogadoIID( TokenInfos.ID_NAO_EXTRAIDO );
		
		Object logadoEIDs = claims.get( "logadoEIDs" );
		if ( logadoEIDs != null ) {			
			String[] split = String.valueOf( logadoEIDs ).split( "\\s*" );
			Long[] eids = new Long[ split.length ];
			for( int i = 0; i < eids.length; i++ )
				eids[ i ] = Long.parseLong( split[ i ] );
			
			tokenInfos.setLogadoEIDs( eids );
		} else {
			tokenInfos.setLogadoEIDs( new Long[] {} );
		}
		
		Object perfil = claims.get( "perfil" );
		if ( perfil != null )
			tokenInfos.setPerfil( String.valueOf( perfil ) );
						
		return tokenInfos;
	}
	
	public Claims getTokenClaims( String token ) {
		return Jwts.parser().setSigningKey( secret ).parseClaimsJws( token ).getBody();
	}	

}
