package sgescolar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.UsuarioBuilder;
import sgescolar.enums.UsuarioPerfilEnumManager;
import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.Administrador;
import sgescolar.model.Aluno;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Professor;
import sgescolar.model.Secretario;
import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupoMap;
import sgescolar.model.request.LoginRequest;
import sgescolar.model.response.LoginResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AdministradorRepository;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.SecretarioRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.util.HashUtil;

@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
			
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private UsuarioBuilder usuarioBuilder;
			
	@Autowired
	private HashUtil hashUtil;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private UsuarioPerfilEnumManager usuarioPerfilEnumManager;

	public LoginResponse login( LoginRequest request ) throws ServiceException  {		
		String username = request.getUsername();
		String password = hashUtil.geraHash( request.getPassword() );
		
		Optional<Usuario> uop = usuarioRepository.findByUsername( username );
		if ( !uop.isPresent() )
			throw new ServiceException( ServiceErro.USUARIO_NAO_ENCONTRADO );
				
		Usuario u = uop.get();
		
		if ( !u.getPassword().equals( password ) )
			throw new ServiceException( ServiceErro.USERNAME_PASSWORD_NAO_CORRESPONDEM );
		
		UsuarioResponse uResp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( uResp, u ); 
		
		List<String> lista = this.listaAuthorities( u );		
		String[] authorities = lista.toArray( new String[ lista.size() ] );
		
		Long uid = uResp.getId();
		String perfil = uResp.getPerfil().getName();
		
		TokenInfos tokenInfos = new TokenInfos();
		tokenInfos.setUsername( request.getUsername() );
		tokenInfos.setAuthorities( authorities ); 
		tokenInfos.setLogadoUID( uid );
		tokenInfos.setLogadoEID( TokenInfos.ID_NAO_EXTRAIDO ); 
		tokenInfos.setPerfil( perfil );
		
		Long entidadeId = uid;
		
		UsuarioPerfil uperfil = usuarioPerfilEnumManager.getEnum( perfil );
		if ( uperfil.isAdmin() ) {
			Optional<Administrador> adminOp = administradorRepository.buscaPorUID( uid );
			if ( !adminOp.isPresent() )
				throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
			
			Administrador admin = adminOp.get();			
			Long iid = admin.getInstituicao().getId();
			tokenInfos.setLogadoIID( iid ); 
			
			entidadeId = admin.getId();;
		} else if ( uperfil.isSecretario() ) {
			Optional<Secretario> sop = secretarioRepository.buscaPorUID( uid );
			if ( !sop.isPresent() )
				throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
			
			Secretario sec = sop.get();			
			Long eid = sec.getEscola().getId();
			tokenInfos.setLogadoEID( eid );
			
			entidadeId = sec.getId();
		} else if ( uperfil.isProfessor() ) {
			Optional<Professor> pop = professorRepository.buscaPorUID( uid );
			if ( !pop.isPresent() )
				throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
			
			entidadeId = pop.get().getId();
		} else if ( uperfil.isAluno() ) {
			Optional<Aluno> aop = alunoRepository.buscaPorUID( uid );
			if ( !aop.isPresent() )
				throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
			
			entidadeId = aop.get().getId();
		}
		
		String token = tokenUtil.geraToken( tokenInfos );
				
		LoginResponse resp = new LoginResponse();
		resp.setUsuario( uResp );
		resp.setPerfil( uResp.getPerfil() ); 
		resp.setToken( token );
		resp.setPermissoes( lista ); 
		resp.setEntidadeId( entidadeId );
		return resp;
	}
	
	private List<String> listaAuthorities( Usuario u ) {
		List<UsuarioGrupoMap> maps = u.getUsuarioGrupoMaps();
		List<String> authorities = new ArrayList<>();
		
		for( UsuarioGrupoMap map : maps ) {		
			int size = map.getGrupo().getPermissaoGrupos().size();
			for( int i = 0; i < size; i++ ) {
				PermissaoGrupo p = map.getGrupo().getPermissaoGrupos().get( i );
				String recurso = p.getRecurso().getNome();
				if ( p.isEscrita() ) {
					String authority = recurso + PermissaoGrupo.PREFIXO_ESCRITA; 
					if ( !this.buscaAuthority( authorities, authority ) )
						authorities.add( authority );
				}
				
				if ( p.isLeitura() ) {
					String authority = recurso + PermissaoGrupo.PREFIXO_LEITURA; 
					if ( !this.buscaAuthority( authorities, authority ) )
						authorities.add( authority );
				}
				
				if ( p.isRemocao() ) {
					String authority = recurso + PermissaoGrupo.PREFIXO_REMOCAO; 
					if ( !this.buscaAuthority( authorities, authority ) )
						authorities.add( authority );
				}
			}
		
		}
		authorities.add( "loginREAD" );
		
		return authorities;
	}
	
	private boolean buscaAuthority( List<String> authorities, String authority ) {
		for( String a : authorities )
			if ( a.equalsIgnoreCase( authority ) )
				return true;
		return false;
	}
	
}
