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

import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.PeriodoService;
import sgescolar.validacao.PeriodoValidator;

@RestController
@RequestMapping(value="/api/periodo")
public class PeriodoController {
	
	@Autowired
	private PeriodoService periodoService;

	@Autowired
	private PeriodoValidator periodoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
				
	@PreAuthorize("hasAuthority('periodoWRITE')")
	@PostMapping(value="/registra/{anoLetivoId}")
	public ResponseEntity<Object> registra( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long anoLetivoId,
			@RequestBody SavePeriodoRequest req ) {	

		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			periodoValidator.validaSaveRequest( req );
			periodoService.registraPeriodo( anoLetivoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('periodoWRITE')")
	@PutMapping(value="/atualiza/{periodoId}")
	public ResponseEntity<Object> atualiza(			
			@RequestHeader("Authorization") String auth,
			@PathVariable Long periodoId, 
			@RequestBody SavePeriodoRequest req ) {	
		
		try {			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			periodoValidator.validaSaveRequest( req );
			periodoService.alteraPeriodo( periodoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('periodoREAD')")
	@GetMapping(value="/get/{periodoId}")
	public ResponseEntity<Object> busca( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long periodoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			PeriodoResponse resp = periodoService.buscaPeriodo( periodoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
	@PreAuthorize("hasAuthority('periodoREAD')")
	@GetMapping(value="/lista/{anoLetivoId}")
	public ResponseEntity<Object> lista( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<PeriodoResponse> lista = periodoService.listaPeriodos( anoLetivoId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('periodoDELETE')")
	@DeleteMapping(value="/deleta/{periodoId}")
	public ResponseEntity<Object> deleta( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long periodoId ) {
		
		try {		
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			periodoService.deletaPeriodo( periodoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}

