package sgescolar.service.dao;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Escola;
import sgescolar.model.Instituicao;
import sgescolar.msg.ServiceErro;
import sgescolar.security.jwt.TokenInfos;

@Component
public class TokenDAO {

	public void autorizaPorEscolaOuInstituicao( Escola escola, TokenInfos infos ) throws TokenAutorizacaoException {
		Long logadoEID = infos.getLogadoEID();
		
		if ( logadoEID != escola.getId() ) {				
			String perfil = infos.getPerfil();
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ADMIN.name() ) && !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new TokenAutorizacaoException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );
			
			Instituicao inst = escola.getInstituicao();
			this.autorizaPorInstituicao( inst, infos ); 						
		} 
	}
	
	public void autorizaPorInstituicao( Instituicao instituicao, TokenInfos infos ) throws TokenAutorizacaoException {
		Long logadoIID = infos.getLogadoIID();

		if ( logadoIID != instituicao.getId() ) {
			String perfil = infos.getPerfil();
			
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new TokenAutorizacaoException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_INSTITUICAO );
		}
	}
}
