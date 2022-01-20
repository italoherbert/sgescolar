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
import sgescolar.model.Matricula;
import sgescolar.model.PermissaoGrupo;
import sgescolar.model.Professor;
import sgescolar.model.ProfessorAlocacao;
import sgescolar.model.Secretario;
import sgescolar.model.Usuario;
import sgescolar.model.UsuarioGrupoMap;
import sgescolar.model.request.LoginRequest;
import sgescolar.model.response.LoginResponse;
import sgescolar.model.response.TipoResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.ServiceErro;
import sgescolar.repository.AdministradorRepository;
import sgescolar.repository.AlunoRepository;
import sgescolar.repository.ProfessorRepository;
import sgescolar.repository.SecretarioRepository;
import sgescolar.repository.UsuarioRepository;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.dao.AnoAtualDAO;
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
	private AnoAtualDAO anoAtualDAO;
	
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
		
		UsuarioResponse uresp = usuarioBuilder.novoUsuarioResponse();
		usuarioBuilder.carregaUsuarioResponse( uresp, u ); 
		
		List<String> lista = this.listaAuthorities( u );		
		String[] authorities = lista.toArray( new String[ lista.size() ] );
		
		Long uid = uresp.getId();
		TipoResponse perfil = uresp.getPerfil();
		
		String perfilName = perfil.getName();		
		UsuarioPerfil uperfil = usuarioPerfilEnumManager.getEnum( perfilName );
		
		Long entidadeId = this.entidadeId( uperfil, uid );
		
		TokenInfos tokenInfos = new TokenInfos();
		tokenInfos.setUsername( request.getUsername() );
		tokenInfos.setAuthorities( authorities ); 
		tokenInfos.setLogadoUID( uid );
		tokenInfos.setPerfil( perfilName );
		
		this.carregaTokenInfosIDs( tokenInfos, uperfil, uid );
								
		String token = tokenUtil.geraToken( tokenInfos );
				
		LoginResponse resp = new LoginResponse();
		resp.setUsuario( uresp );
		resp.setPerfil( perfil ); 
		resp.setToken( token );
		resp.setPermissoes( lista ); 
		resp.setEntidadeId( entidadeId );
		return resp;
	}
	
	private void carregaTokenInfosIDs( TokenInfos tokenInfos, UsuarioPerfil uperfil, Long uid ) throws ServiceException {
		switch( uperfil ) {
			case ADMIN:
				Optional<Administrador> adminOp = administradorRepository.buscaPorUID( uid );
				if ( !adminOp.isPresent() )
					throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
				
				Administrador admin = adminOp.get();			
				Long iid = admin.getInstituicao().getId();
				tokenInfos.setLogadoIID( iid );
				break;
			case SECRETARIO:
				Optional<Secretario> sop = secretarioRepository.buscaPorUID( uid );
				if ( !sop.isPresent() )
					throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
				
				Secretario sec = sop.get();			
				tokenInfos.setLogadoEIDs( this.secretarioLogadoEIDs( sec ) );			
				break;
			case PROFESSOR:
				Optional<Professor> pop = professorRepository.buscaPorUID( uid );
				if ( !pop.isPresent() )
					throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
				
				Professor prof = pop.get();
				tokenInfos.setLogadoEIDs( this.professorLogadoEIDs( prof ) );
				break;
			case ALUNO:
				Optional<Aluno> aop = alunoRepository.buscaPorUID( uid );
				if ( !aop.isPresent() )
					throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
				
				Aluno aluno = aop.get();
				tokenInfos.setLogadoEIDs( this.alunoLogadoEIDs( aluno ) );
				break;
			default:				
		}		
	}
	
	private Long entidadeId( UsuarioPerfil uperfil, Long uid ) throws ServiceException {
		switch ( uperfil ) {
			case ADMIN:
				Optional<Administrador> adminOp = administradorRepository.buscaPorUID( uid );
				if ( !adminOp.isPresent() )
					throw new ServiceException( ServiceErro.ADMINISTRADOR_NAO_ENCONTRADO );
				
				return adminOp.get().getId();
			case SECRETARIO:
				Optional<Secretario> sop = secretarioRepository.buscaPorUID( uid );
				if ( !sop.isPresent() )
					throw new ServiceException( ServiceErro.SECRETARIO_NAO_ENCONTRADO );
				
				return sop.get().getId();						
			case PROFESSOR:
				Optional<Professor> pop = professorRepository.buscaPorUID( uid );
				if ( !pop.isPresent() )
					throw new ServiceException( ServiceErro.PROFESSOR_NAO_ENCONTRADO );
				
				return pop.get().getId();			
			case ALUNO:
				Optional<Aluno> aop = alunoRepository.buscaPorUID( uid );
				if ( !aop.isPresent() )
					throw new ServiceException( ServiceErro.ALUNO_NAO_ENCONTRADO );
				
				return aop.get().getId();				
			default:
				return -1L;
		}	
	}
	
	private Long[] professorLogadoEIDs( Professor p ) {
		List<ProfessorAlocacao> alocacoes = anoAtualDAO.buscaProfessorAlocacoesPorAno( p.getId() ); 
		List<Long> eids = new ArrayList<>();
		int size = alocacoes.size();
		for( int i = 0; i < size; i++ ) {
			Long eid = alocacoes.get( i ).getEscola().getId();
			
			boolean achou = false;
			int size2 = eids.size();
			for( int j = 0; !achou && j < size2; j++ ) {
				if ( eid == eids.get( j ) )
					achou = true;				
			}
			
			if ( !achou )
				eids.add( eid ); 
		}
		
		Long[] vet = new Long[ eids.size() ];
		return eids.toArray( vet ); 
	}
	
	private Long[] alunoLogadoEIDs( Aluno a ) throws ServiceException {
		Matricula matricula = anoAtualDAO.buscaMatriculaPorAnoAtual( a.getId() );		
		Long eid = matricula.getTurma().getAnoLetivo().getEscola().getId();
		
		return new Long[] { eid };
	}
	
	private Long[] secretarioLogadoEIDs( Secretario s ) {				
		return new Long[] { s.getEscola().getId() };
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
