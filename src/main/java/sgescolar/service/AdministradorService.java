package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.AdministradorBuilder;
import sgescolar.model.Administrador;
import sgescolar.model.Instituicao;
import sgescolar.model.Usuario;
import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.model.request.filtro.FiltraAdministradoresRequest;
import sgescolar.model.response.AdministradorResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AdministradorRepository;
import sgescolar.repository.InstituicaoRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.TokenAutorizacaoException;
import sgescolar.service.dao.TokenDAO;
import sgescolar.service.dao.UsuarioDAO;

@Service
public class AdministradorService {
		
	@Autowired
	private InstituicaoRepository instituicaoituicaoRepository;
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private TokenDAO tokenDAO;
			
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
	public void registraAdministrador( Long instituicaoId, SaveAdministradorRequest request, TokenInfos tokenInfos ) throws ServiceException {		
		Optional<Usuario> uop = usuarioRepository.findByUsername( request.getFuncionario().getUsuario().getUsername() );
		if ( uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_JA_EXISTE );
				
		Optional<Instituicao> instituicaoOp = instituicaoituicaoRepository.findById( instituicaoId );
		if ( !instituicaoOp.isPresent() )
			throw new ServiceException( ServiceErro.INSTITUICAO_NAO_ENCONTRADA );
		
		Instituicao instituicao = instituicaoOp.get();		
		tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos );
		
		Administrador adm = administradorBuilder.novoAdministrador( instituicao );
		administradorBuilder.carregaAdministrador( adm, request );		
		administradorRepository.save( adm );
		
		usuarioDAO.salvaUsuarioGrupoMaps( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 
	}
	
	@Transactional
	public void alteraAdministrador( Long administradorId, SaveAdministradorRequest request, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador adm = admOp.get();
		
		Instituicao instituicao = adm.getInstituicao();		
		tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos );
						
		usuarioDAO.validaAlteracao( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 

		administradorBuilder.carregaAdministrador( adm, request );		
		administradorRepository.save( adm );

		usuarioDAO.salvaUsuarioGrupoMaps( adm.getFuncionario().getUsuario(), request.getFuncionario().getUsuario() ); 		
	}
	
	public List<AdministradorResponse> filtraAdministradores( FiltraAdministradoresRequest request, TokenInfos tokenInfos ) {
		String usernameIni = request.getUsernameIni();
		if ( usernameIni.equals( "*" ) )
			usernameIni = "";
		usernameIni += "%";
		
		List<Administrador> administradores = administradorRepository.filtra( usernameIni );
		
		List<AdministradorResponse> lista = new ArrayList<>();
		for( Administrador adm : administradores ) {
			try {
				Instituicao instituicao = adm.getInstituicao();				
				tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos ); 
				
				AdministradorResponse resp = administradorBuilder.novoAdministradorResponse();
				administradorBuilder.carregaAdministradorResponse( resp, adm );
				
				lista.add( resp );
			} catch ( TokenAutorizacaoException e ) {
				
			}
		}
		
		return lista;
	}
	
	public AdministradorResponse buscaAdministrador( Long administradorId, TokenInfos tokenInfos ) throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador adm = admOp.get();		
		Instituicao instituicao = adm.getInstituicao();
		
		tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos ); 
		
		AdministradorResponse resp = administradorBuilder.novoAdministradorResponse();
		administradorBuilder.carregaAdministradorResponse( resp, adm );		
		return resp;
	}
	
	public void deletaAdministrador( Long administradorId, TokenInfos tokenInfos )  throws ServiceException {
		Optional<Administrador> admOp = administradorRepository.findById( administradorId );
		if ( !admOp.isPresent() )
			throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
		
		Administrador adm = admOp.get();
		Instituicao instituicao = adm.getInstituicao();
		
		tokenDAO.autorizaPorInstituicao( instituicao, tokenInfos ); 
		
		administradorRepository.deleteById( administradorId ); 
	}
	
}
