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

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.request.FiltraSecretariosRequest;
import sgescolar.model.request.SaveSecretarioRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.SecretarioResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.SecretarioService;
import sgescolar.service.filtro.FiltroManager;
import sgescolar.service.filtro.FiltroSecretarios;
import sgescolar.validacao.SecretarioValidator;
import sgescolar.validacao.TokenInfosValidator;

@RestController
@RequestMapping(value="/api/secretario") 
public class SecretarioController {

	@Autowired
	private SecretarioService secretarioService;
	
	@Autowired
	private SecretarioValidator secretarioValidator;
	
	@Autowired
	private TokenInfosValidator tokenInfosValidator;
	
	@Autowired
	private FiltroManager filtroManager;
			
	@PreAuthorize("hasAuthority('secretarioWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( 
			@RequestHeader("Authorization") String auth,
			@RequestBody SaveSecretarioRequest req ) {	

		try {
			Long eid = tokenInfosValidator.validaEIDOuAdmin( auth, req.getEscolaId() );
			secretarioValidator.validaSaveRequest( req );
			secretarioService.registraSecretario( eid, req );
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
			TokenInfos tinfos = tokenInfosValidator.validaTokenInfos( auth );
			Long logadoUID = tinfos.getLogadoUID();
			
			secretarioValidator.validaSaveRequest( req );
			secretarioService.verificaSeDono( logadoUID, secretarioId );
			secretarioService.alteraSecretario( secretarioId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('secretarioREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( 
			@RequestHeader("Authorization") String auth,
			@RequestBody FiltraSecretariosRequest request ) {
		try {
			TokenInfos tokenInfos = tokenInfosValidator.validaTokenInfos( auth, UsuarioPerfil.ADMIN, UsuarioPerfil.SECRETARIO );
			FiltroSecretarios filtro = filtroManager.novoFiltroSecretarios( tokenInfos );
			
			secretarioValidator.validaFiltroRequest( request );
			List<SecretarioResponse> lista = secretarioService.filtraSecretarios( request, filtro );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('secretarioREAD')")
	@GetMapping(value="/get/{secretarioId}")
	public ResponseEntity<Object> busca( @PathVariable Long secretarioId ) {				
		try {
			SecretarioResponse resp = secretarioService.buscaSecretario( secretarioId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('secretarioDELETE')")
	@DeleteMapping(value="/deleta/{secretarioId}")
	public ResponseEntity<Object> deleta( @PathVariable Long secretarioId ) {		
		try {			
			secretarioService.deletaSecretario( secretarioId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}
