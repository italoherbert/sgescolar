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

import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.request.filtro.FiltraSecretariosRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.SecretarioService;
import sgescolar.validacao.SecretarioValidator;

@RestController
@RequestMapping(value="/api/secretario") 
public class SecretarioController {

	@Autowired
	private SecretarioService secretarioService;
	
	@Autowired
	private SecretarioValidator secretarioValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
				
	@PreAuthorize("hasAuthority('secretarioWRITE')")
	@PostMapping(value="/registra/{escolaId}")
	public ResponseEntity<Object> registra( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody SaveSecretarioRequest req ) {	

		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			secretarioValidator.validaSaveRequest( req );
			secretarioService.registraSecretario( escolaId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('secretarioWRITE')")
	@PutMapping(value="/atualiza/{secretarioId}")
	public ResponseEntity<Object> atualiza(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long secretarioId, 
			@RequestBody SaveSecretarioRequest req ) {	
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			secretarioValidator.validaSaveRequest( req );
			secretarioService.alteraSecretario( secretarioId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('secretarioREAD')")
	@PostMapping(value="/filtra/{escolaId}")
	public ResponseEntity<Object> filtra( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody FiltraSecretariosRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			secretarioValidator.validaFiltroRequest( request );
			List<SecretarioResponse> lista = secretarioService.filtraSecretarios( escolaId, request, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('secretarioREAD')")
	@GetMapping(value="/get/{secretarioId}")
	public ResponseEntity<Object> busca( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long secretarioId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			SecretarioResponse resp = secretarioService.buscaSecretario( secretarioId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('secretarioDELETE')")
	@DeleteMapping(value="/deleta/{secretarioId}")
	public ResponseEntity<Object> deleta( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long secretarioId ) {
		
		try {			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			secretarioService.deletaSecretario( secretarioId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}
