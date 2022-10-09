package sgescolar.service.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.builder.UsuarioGrupoMapBuilder;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupo;
import sgescolar.model.UsuarioGrupoMap;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.UsuarioGrupoMapRepository;
import sgescolar.repository.UsuarioGrupoRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.ServiceException;

@Component
public class UsuarioDAO {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	@Autowired
	private UsuarioGrupoMapRepository usuarioGrupoMapRepository;	
		
	@Autowired
	private UsuarioGrupoMapBuilder usuarioGrupoMapBuilder;
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;
	
	public void validaAlteracao( Usuario u, SaveUsuarioRequest request ) throws ServiceException {
		String username = u.getUsername();
		String usernameNovo = request.getUsername();
		
		if ( !username.equalsIgnoreCase( usernameNovo ) )
			if ( usuarioRepository.findByUsername( usernameNovo ).isPresent() )
				throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
		
		UsuarioPerfil perfil = usuarioPerfilEnumManager.getEnum( request.getPerfil() );
		if ( perfil != u.getPerfil() )
			throw new ServiceException( ServiceErro.PERFIL_NAO_ALTERAVEL );
	}
	
	@Transactional
	public void salvaUsuarioGrupoMaps( Usuario u, SaveUsuarioRequest request ) throws ServiceException {								
		boolean temPerfilGrupo = false;
			
		List<UsuarioGrupoMap> maps = u.getUsuarioGrupoMaps();	
		if ( maps != null )
			maps.clear();
		
		String perfil = request.getPerfil();
		String[] grupos = request.getGrupos();
		for( String grupoStr : grupos ) {
			if ( grupoStr.equalsIgnoreCase( perfil ) )
				temPerfilGrupo = true;
			
			Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorNome( grupoStr );
			if ( !ugOp.isPresent() )
				throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );

			UsuarioGrupo ug = ugOp.get();
									
			usuarioGrupoMapRepository.save( usuarioGrupoMapBuilder.novoUsuarioGrupoMap( u, ug ) );
		}
		
		if ( !temPerfilGrupo ) {
			Optional<UsuarioGrupo> ugOp = usuarioGrupoRepository.buscaPorNome( perfil );
			if ( !ugOp.isPresent() )
				throw new ServiceException( ServiceErro.USUARIO_GRUPO_NAO_ENCONTRADO );
			
			UsuarioGrupo ug = ugOp.get();
			
			usuarioGrupoMapRepository.save( usuarioGrupoMapBuilder.novoUsuarioGrupoMap( u, ug ) );
		}		
	}
		
}
