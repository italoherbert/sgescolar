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
import sgescolar.service.AnoLetivoService;
import sgescolar.validacao.AnoLetivoValidator;
import sgescolar.validacao.TokenInfosValidator;

@RestController
@RequestMapping(value="/api/anoletivo")
public class AnoLetivoController {
	
	@Autowired
	private AnoLetivoService anoLetivoService;

	@Autowired
	private AnoLetivoValidator anoLetivoValidator;
	
	@Autowired
	private TokenInfosValidator tokenInfosValidator;
				
	@PreAuthorize("hasAuthority('anoLetivoWRITE')")
	@PostMapping(value="/registra/{escolaId}")
	public ResponseEntity<Object> registra( 
			@RequestHeader("Authorization") String auth,
			@PathVariable String escolaId,
			@RequestBody SaveAnoLetivoRequest req ) {	

		try {
			Long eid = tokenInfosValidator.validaEIDOuAdmin( auth, escolaId );
			anoLetivoValidator.validaSaveRequest( req );
			anoLetivoService.registraAnoLetivo( eid, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoWRITE')")
	@PutMapping(value="/atualiza/{escolaId}/{anoLetivoId}")
	public ResponseEntity<Object> atualiza(			
			@RequestHeader("Authorization") String auth,
			@PathVariable String escolaId,
			@PathVariable Long anoLetivoId, 
			@RequestBody SaveAnoLetivoRequest req ) {	
		
		try {			
			tokenInfosValidator.validaEIDOuAdmin( auth, escolaId );
			anoLetivoValidator.validaSaveRequest( req );
			anoLetivoService.alteraAnoLetivo( anoLetivoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/busca/{escolaId}/{ano}")
	public ResponseEntity<Object> busca(
			@RequestHeader("Authorization") String auth,
			@PathVariable String escolaId,
			@PathVariable String ano ) {
		
		try {						
			Long eid = tokenInfosValidator.validaEIDOuAdmin( auth, escolaId );
			
			anoLetivoValidator.validaBuscaRequest( ano );
			AnoLetivoResponse resp = anoLetivoService.buscaAnoLetivoPorAno( eid, ano );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/lista/{escolaId}")
	public ResponseEntity<Object> lista(
			@RequestHeader("Authorization") String auth,
			@PathVariable String escolaId ) {
		
		try {						
			Long eid = tokenInfosValidator.validaEIDOuAdmin( auth, escolaId );
			
			List<AnoLetivoResponse> resps = anoLetivoService.listaTodosPorEscola( eid );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoREAD')")
	@GetMapping(value="/get/{anoLetivoId}")
	public ResponseEntity<Object> busca( @PathVariable Long anoLetivoId ) {		
		try {
			AnoLetivoResponse resp = anoLetivoService.buscaAnoLetivo( anoLetivoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('anoLetivoDELETE')")
	@DeleteMapping(value="/deleta/{anoLetivoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long anoLetivoId ) {		
		try {			
			anoLetivoService.deletaAnoLetivo( anoLetivoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
