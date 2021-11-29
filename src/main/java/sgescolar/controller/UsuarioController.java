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

import sgescolar.model.request.FiltraUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.UsuarioService;
import sgescolar.validacao.UsuarioValidator;

@RestController
@RequestMapping(value="/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PreAuthorize("hasAuthority('usuarioWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra(
			@RequestHeader( "Authorization") String auth,
			@RequestBody SaveUsuarioRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			usuarioValidator.validaSaveRequest( req );
			usuarioService.registraUsuario( req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('usuarioWRITE')")
	@PutMapping(value="/atualiza/{usuarioId}")
	public ResponseEntity<Object> atualiza( 
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long usuarioId, 
			@RequestBody SaveUsuarioRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			usuarioValidator.validaSaveRequest( req );
			usuarioService.alteraUsuario( usuarioId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('usuarioREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( 
			@RequestHeader( "Authorization") String auth,
			@RequestBody FiltraUsuariosRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			usuarioValidator.validaFiltraRequest( request );
			List<UsuarioResponse> lista = usuarioService.filtraUsuarios( request, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 					
	}
	
	@PreAuthorize("hasAuthority('usuarioREAD')")
	@GetMapping(value="/get/{usuarioId}")
	public ResponseEntity<Object> busca( 
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long usuarioId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			UsuarioResponse resp = usuarioService.buscaUsuario( usuarioId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('usuarioDELETE')")
	@DeleteMapping(value="/deleta/{usuarioId}")
	public ResponseEntity<Object> deleta( 
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long usuarioId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			usuarioService.deletaUsuario( usuarioId, tokenInfos );
			return ResponseEntity.ok( usuarioId );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
