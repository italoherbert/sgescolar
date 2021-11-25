package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.util.AdministradorBuilder;
import sgescolar.model.Administrador;
import sgescolar.model.Usuario;
import sgescolar.model.request.FiltraAdministradoresRequest;
import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.model.response.AdministradorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AdministradorRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class AdministradorService {
		
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@Autowired
	private UsuarioDAO usuarioDAO;
			
	@Autowired
	private AdministradorBuilder administradorBuilder;
	
	public void verificaSeDono( Long logadoUID, Long administradorId ) throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador p = admOp.get();
		Long uid = p.getFuncionario().getUsuario().getId();
		
		if ( logadoUID != uid )
			throw new ServiceException( ServiceErro.NAO_EH_DONO );
	}
	
	@Transactional
	public void registraAdministrador( SaveAdministradorRequest request ) throws ServiceException {		
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getFuncionario().getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );								
		
		Administrador adm = administradorBuilder.novoAdministrador();
		administradorBuilder.carregaAdministrador( adm, request );		
		administradorRepository.save( adm );
		
		usuarioDAO.salvaUsuarioGrupoMaps( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}
	
	@Transactional
	public void alteraAdministrador( Long administradorId, SaveAdministradorRequest request ) throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador adm = admOp.get();
				
		usuarioDAO.validaAlteracao( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 

		administradorBuilder.carregaAdministrador( adm, request );		
		administradorRepository.save( adm );

		usuarioDAO.salvaUsuarioGrupoMaps( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 		
	}
	
	public List<AdministradorResponse> filtraAdministradores( FiltraAdministradoresRequest request ) {
		String usernameIni = request.getUsernameIni();
		if ( usernameIni.equals( "*" ) )
			usernameIni = "";
		usernameIni += "%";
		
		List<Administrador> administradores = administradorRepository.filtra( usernameIni );
		
		List<AdministradorResponse> lista = new ArrayList<>();
		for( Administrador adm : administradores ) {
			AdministradorResponse resp = administradorBuilder.novoAdministradorResponse();
			administradorBuilder.carregaAdministradorResponse( resp, adm );
			
			lista.add( resp );
		}
		
		return lista;
	}
	
	public AdministradorResponse buscaAdministrador( Long administradorId ) throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador adm = admOp.get();
		
		AdministradorResponse resp = administradorBuilder.novoAdministradorResponse();
		administradorBuilder.carregaAdministradorResponse( resp, adm );		
		return resp;
	}
	
	public void deletaAdministrador( Long administradorId )  throws ServiceException {
		boolean existe = administradorRepository.existsById( administradorId );
		if ( !existe )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		administradorRepository.deleteById( administradorId ); 
	}
	
}
