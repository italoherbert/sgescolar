package sgescolar.validacao;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.model.enums.UsuarioPerfil;
import sgescolar.msg.ValidacaoErro;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;

@Component
public class TokenInfosValidator {
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
		
	public void validaEID( String authorization, Long eid ) throws ValidacaoException {
		if ( eid == null )
			throw new ValidacaoException( ValidacaoErro.EID_NULO );
				
		Long tokenEID = jwtTokenUtil.getEID( authorization );
		if ( tokenEID != eid )
			throw new ValidacaoException( ValidacaoErro.EID_NAO_CORRESPONDE_AO_DO_TOKEN );
	}
	
	public TokenInfos validaTokenInfos( String authorization, UsuarioPerfil... perfis ) throws ValidacaoException {
		TokenInfos tinfos = jwtTokenUtil.getBearerTokenInfos( authorization );		
		usuarioPerfilEnumManager.enumValida( tinfos.getPerfil() );
		
		Long uid = tinfos.getLogadoUID();
		Long eid = tinfos.getLogadoEID();
		
		if ( uid == TokenInfos.ID_NAO_EXTRAIDO )
			throw new ValidationException( ValidacaoErro.UID_NAO_EXTRAIDO_DE_TOKEN );			
		
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( tinfos.getPerfil() );
		if ( perfis.length > 0 ) {
			boolean achou = false;
			for( int i = 0; !achou && i < perfis.length; i++ )
				if ( perfil == perfis[ i ] )
					achou = true;
			if ( !achou )
				throw new ValidationException( ValidacaoErro.USUARIO_PERFIL_NAO_CORRESPONDE_AO_ESPERADO );
		}
		if( perfil == UsuarioPerfil.SECRETARIO ) {
			if ( eid == TokenInfos.ID_NAO_EXTRAIDO )
				throw new ValidationException( ValidacaoErro.EID_NAO_EXTRAIDO_DE_TOKEN );			
		} else {
			if ( eid != TokenInfos.ID_NAO_EXTRAIDO )
				throw new ValidacaoException( ValidacaoErro.EID_NAO_DEVERIA_SER_EXTRAIDO_DE_TOKEN );
		}		
		
		return tinfos;
	}
			
}
