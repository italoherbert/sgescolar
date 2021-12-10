package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveAdministradorRequest;
import sgescolar.model.request.filtro.FiltraAdministradoresRequest;
import sgescolar.model.response.AdministradorResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.AdministradorService;
import sgescolar.validacao.AdministradorValidator;

@RestController
@RequestMapping(value="/api/administrador") 
public class AdministradorController {

	@Autowired
	private AdministradorService administradorService;
	
	@Autowired
	private AdministradorValidator administradorValidator;
			
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('administradorWRITE')")
	@PostMapping(value="/registra/{instituicaoId}")
	public ResponseEntity<Object> registra( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long instituicaoId,
			@RequestBody SaveAdministradorRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			administradorValidator.validaSaveRequest( req );
			administradorService.registraAdministrador( instituicaoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('administradorWRITE')")
	@PutMapping(value="/atualiza/{administradorId}")
	public ResponseEntity<Object> atualiza(  
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long administradorId, 
			@RequestBody SaveAdministradorRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			administradorValidator.validaSaveRequest( req );
			administradorService.alteraAdministrador( administradorId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('administradorREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra(   
			@RequestHeader( "Authorization" ) String auth, 
			@RequestBody FiltraAdministradoresRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			administradorValidator.validaFiltroRequest( request );
			List<AdministradorResponse> lista = administradorService.filtraAdministradores( request, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('administradorREAD')")
	@GetMapping(value="/get/{administradorId}")
	public ResponseEntity<Object> busca(   
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long administradorId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			AdministradorResponse resp = administradorService.buscaAdministrador( administradorId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('administradorDELETE')")
	@DeleteMapping(value="/deleta/{administradorId}")
	public ResponseEntity<Object> deleta(    
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long administradorId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			administradorService.deletaAdministrador( administradorId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}