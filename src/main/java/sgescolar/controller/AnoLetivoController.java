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

import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.response.AnoLetivoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.AnoLetivoService;
import sgescolar.validacao.AnoLetivoValidator;

@RestController
@RequestMapping(value="/api/anoletivo")
public class AnoLetivoController {
	
	@Autowired
	private AnoLetivoService anoLetivoService;

	@Autowired
	private AnoLetivoValidator anoLetivoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
				
	@PreAuthorize("hasAuthority('anoLetivoWRITE')")
	@PostMapping(value="/registra/{escolaId}")
	public ResponseEntity<Object> registra( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody SaveAnoLetivoRequest req ) {	

		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			anoLetivoValidator.validaSaveRequest( req );
			anoLetivoService.registraAnoLetivo( escolaId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoWRITE')")
	@PutMapping(value="/atualiza/{anoLetivoId}")
	public ResponseEntity<Object> atualiza(			
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId, 
			@RequestBody SaveAnoLetivoRequest req ) {	
		
		try {			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			anoLetivoValidator.validaSaveRequest( req );
			anoLetivoService.alteraAnoLetivo( anoLetivoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/busca/{escolaId}/{ano}")
	public ResponseEntity<Object> busca(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@PathVariable String ano ) {
		
		try {									
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );						
			anoLetivoValidator.validaBuscaRequest( ano );
			AnoLetivoResponse resp = anoLetivoService.buscaAnoLetivoPorAno( escolaId, ano, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/lista/{escolaId}")
	public ResponseEntity<Object> lista(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId ) {
		
		try {						
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );						
			List<AnoLetivoResponse> resps = anoLetivoService.listaTodosPorEscola( escolaId, tokenInfos );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/get/{anoLetivoId}")
	public ResponseEntity<Object> busca(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );						
			AnoLetivoResponse resp = anoLetivoService.buscaAnoLetivo( anoLetivoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoDELETE')")
	@DeleteMapping(value="/deleta/{anoLetivoId}")
	public ResponseEntity<Object> deleta( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId ) {
		
		try {			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );						
			anoLetivoService.deletaAnoLetivo( anoLetivoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
