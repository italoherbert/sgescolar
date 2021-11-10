package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.msg.ValidacaoErro;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class TokenInfosValidator {
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public Long validaUID( String authorization, String suid ) throws ValidacaoException {		
		if ( !validatorUtil.longValido( suid ) )
			throw new ValidacaoException( ValidacaoErro.UID_INVALIDO );
		
		Long uid = conversorUtil.stringParaLong( suid );		
		Long logadoUID = jwtTokenUtil.getUID( authorization );
				
		if ( uid != logadoUID )
			throw new ValidacaoException( ValidacaoErro.UID_NAO_CORRESPONDE_AO_DO_TOKEN );
		
		return uid;
	}
		
	public Long validaEIDOuAdmin( String authorization, String seid ) throws ValidacaoException {		
		if ( !validatorUtil.longValido( seid ) )
			throw new ValidacaoException( ValidacaoErro.EID_INVALIDO );
		
		Long eid = conversorUtil.stringParaLong( seid );

		String perfilStr = jwtTokenUtil.getPerfil( authorization );
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( perfilStr );
		
		if ( perfil.isAdmin() )
			return eid;
		
		this.validaEID( authorization, eid );		
		return eid;
	}

	public Long validaEID( String authorization, String seid ) throws ValidacaoException {
		if ( !validatorUtil.longValido( seid ) )
			throw new ValidacaoException( ValidacaoErro.EID_INVALIDO );
		
		Long eid = conversorUtil.stringParaLong( seid );		
		
		this.validaEID( authorization, eid );		
		return eid;
	}
	
	public void validaEID( String authorization, Long eid ) throws ValidacaoException {
		if ( eid == null )
			throw new ValidacaoException( ValidacaoErro.EID_NULO );						
		
		Long tokenEID = jwtTokenUtil.getEID( authorization );
		if ( tokenEID != eid || tokenEID == TokenInfos.ID_NAO_EXTRAIDO )
			throw new ValidacaoException( ValidacaoErro.EID_NAO_CORRESPONDE_AO_DO_TOKEN );		
	}
	
	public TokenInfos validaTokenInfos( String authorization, UsuarioPerfil... perfis ) throws ValidacaoException {
		TokenInfos tinfos = jwtTokenUtil.getBearerTokenInfos( authorization );		
		usuarioPerfilEnumManager.enumValida( tinfos.getPerfil() );
		
		Long uid = tinfos.getLogadoUID();
		Long eid = tinfos.getLogadoEID();
		
		if ( uid == TokenInfos.ID_NAO_EXTRAIDO )
			throw new ValidacaoException( ValidacaoErro.UID_NAO_EXTRAIDO_DE_TOKEN );			
		
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tinfos.getPerfil() );
		if ( perfis.length > 0 ) {
			boolean achou = false;
			for( int i = 0; !achou && i < perfis.length; i++ )
				if ( perfil == perfis[ i ] )
					achou = true;
			if ( !achou )
				throw new ValidacaoException( ValidacaoErro.USUARIO_PERFIL_NAO_CORRESPONDE_AO_ESPERADO );
		}
		
		if( perfil.isSecretarioOuDiretor() ) {
			if ( eid == TokenInfos.ID_NAO_EXTRAIDO )
				throw new ValidacaoException( ValidacaoErro.EID_NAO_EXTRAIDO_DE_TOKEN );			
		} else {
			if ( eid != TokenInfos.ID_NAO_EXTRAIDO )
				throw new ValidacaoException( ValidacaoErro.EID_NAO_DEVERIA_SER_EXTRAIDO_DE_TOKEN );
		}		
		
		return tinfos;
	}
			
}
