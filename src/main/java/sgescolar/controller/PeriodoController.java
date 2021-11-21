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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.PeriodoService;
import sgescolar.validacao.PeriodoValidator;

@RestController
@RequestMapping(value="/api/periodo")
public class PeriodoController {
	
	@Autowired
	private PeriodoService periodoService;

	@Autowired
	private PeriodoValidator periodoValidator;
				
	@PreAuthorize("hasAuthority('periodoWRITE')")
	@PostMapping(value="/registra/{anoLetivoId}")
	public ResponseEntity<Object> registra( 
			@PathVariable Long anoLetivoId,
			@RequestBody SavePeriodoRequest req ) {	

		try {
			periodoValidator.validaSaveRequest( req );
			periodoService.registraPeriodo( anoLetivoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('periodoWRITE')")
	@PutMapping(value="/atualiza/{periodoId}")
	public ResponseEntity<Object> atualiza(			
			@PathVariable Long periodoId, 
			@RequestBody SavePeriodoRequest req ) {	
		
		try {			
			periodoValidator.validaSaveRequest( req );
			periodoService.alteraPeriodo( periodoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('periodoREAD')")
	@GetMapping(value="/get/{periodoId}")
	public ResponseEntity<Object> busca( @PathVariable Long periodoId ) {		
		try {
			PeriodoResponse resp = periodoService.buscaPeriodo( periodoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
	@PreAuthorize("hasAuthority('periodoREAD')")
	@GetMapping(value="/lista/{anoLetivoId}")
	public ResponseEntity<Object> lista( @PathVariable Long anoLetivoId ) {		
		try {
			List<PeriodoResponse> lista = periodoService.listaPeriodos( anoLetivoId );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('periodoDELETE')")
	@DeleteMapping(value="/deleta/{periodoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long periodoId ) {		
		try {			
			periodoService.deletaPeriodo( periodoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}

