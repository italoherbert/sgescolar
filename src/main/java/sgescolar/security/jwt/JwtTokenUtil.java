package sgescolar.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sgescolar.msg.JwtInfoErro;

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
		Long logadoEID = tokenInfos.getLogadoEID();
		
		claims.put( "authorities", strAuthorities );
		claims.put( "logadoUID", logadoUID );
		claims.put( "logadoEID", logadoEID );
		
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
				
	public Long getUID( String authHeader ) throws JwtInfoException {
		Long uid = this.getBearerTokenInfos( authHeader ).getLogadoUID();
		if ( uid == TokenInfos.ID_NAO_EXTRAIDO )
			throw new JwtInfoException( JwtInfoErro.UID_NAO_EXTRAIDO_DE_TOKEN );		
		return uid;
	}
	
	public Long getEID( String authHeader ) throws JwtInfoException {
		Long eid = this.getBearerTokenInfos( authHeader ).getLogadoEID();
		if ( eid == TokenInfos.ID_NAO_EXTRAIDO )
			throw new JwtInfoException( JwtInfoErro.EID_NAO_EXTRAIDO_DE_TOKEN );		
		return eid;
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
		
		Object logadoEID = claims.get( "logadoEID" );
		if ( logadoEID != null )
			tokenInfos.setLogadoEID( Long.parseLong( String.valueOf( logadoUID ) ) );
		else tokenInfos.setLogadoEID( TokenInfos.ID_NAO_EXTRAIDO );
						
		return tokenInfos;
	}
	
	public Claims getTokenClaims( String token ) {
		return Jwts.parser().setSigningKey( secret ).parseClaimsJws( token ).getBody();
	}	

}
