package sgescolar.service.dao;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.msg.ServiceErro;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ServiceException;

@Component
public class TokenDAO {

	public void autorizaPorEscola( Long escolaId, TokenInfos infos ) throws ServiceException {
		Long logadoEID = infos.getLogadoEID();
		
		if ( logadoEID != escolaId ) {		
			String perfil = infos.getPerfil();
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ADMIN.name() ) && !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new ServiceException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );				
		}
	}
	
	public void autorizaPorInstituicao( Long instituicaoId, TokenInfos infos ) throws ServiceException {
		Long logadoIID = infos.getLogadoIID();
		
		if ( logadoIID != instituicaoId ) {
			String perfil = infos.getPerfil();
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new ServiceException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_INSTITUICAO );	
		}
	}
	
}
