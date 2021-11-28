package sgescolar.service.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Escola;
import sgescolar.model.Instituicao;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.EscolaRepository;
import sgescolar.security.jwt.TokenInfos;

@Component
public class TokenDAO {

	@Autowired
	private EscolaRepository escolaRepository;
	
	public void autorizaPorEscolaOuInstituicao( Escola escola, TokenInfos tokenInfos ) throws TokenAutorizacaoException {		
		if ( !this.validaEID( tokenInfos, escola.getId() ) ) { 				
			String perfil = tokenInfos.getPerfil();
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.ADMIN.name() ) && !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new TokenAutorizacaoException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_ESCOLA );
			
			Instituicao inst = escola.getInstituicao();
			this.autorizaPorInstituicao( inst, tokenInfos ); 						
		} 
	}
	
	public void autorizaPorInstituicao( Instituicao instituicao, TokenInfos tokenInfos ) throws TokenAutorizacaoException {
		if ( !this.validaIIDPorEIDs( tokenInfos, instituicao.getId() ) ) { 
			String perfil = tokenInfos.getPerfil();			
			if ( !perfil.equalsIgnoreCase( UsuarioPerfil.RAIZ.name() ) )
				throw new TokenAutorizacaoException( ServiceErro.SEM_PERMISSAO_POR_ESCOPO_INSTITUICAO );
		}
	}
	
	private boolean validaEID( TokenInfos tokenInfos, Long eid ) {
		Long[] logadoEIDs = tokenInfos.getLogadoEIDs();
		for( Long logadoEID : logadoEIDs )
			if ( logadoEID == eid )
				return true;		
		return false;
	}
	
	private boolean validaIIDPorEIDs( TokenInfos tokenInfos, Long iid ) throws TokenAutorizacaoException {
		if ( iid == tokenInfos.getLogadoIID() )
			return true;
		
		Long[] logadoEIDs = tokenInfos.getLogadoEIDs();		
		
		for( Long logadoEID : logadoEIDs ) {
			Optional<Escola> eop = escolaRepository.findById( logadoEID );
			if ( !eop.isPresent() )
				throw new TokenAutorizacaoException( ServiceErro.ESCOLA_NAO_ENCONTRADA );
			
			Instituicao inst = eop.get().getInstituicao();
			if ( inst.getId() == iid )
				return true;
		}
		return false;
	}
		
}
