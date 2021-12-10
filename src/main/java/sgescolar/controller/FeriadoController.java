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

import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.model.request.filtro.FiltraFeriadoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.FeriadoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.FeriadoService;
import sgescolar.validacao.FeriadoValidator;

@RestController
@RequestMapping(value="/api/feriado")
public class FeriadoController {
	
	@Autowired
	private FeriadoService feriadoService;

	@Autowired
	private FeriadoValidator feriadoValidator;
				
	@PreAuthorize("hasAuthority('feriadoWRITE')")
	@PostMapping(value="/registra/{anoLetivoId}")
	public ResponseEntity<Object> registra( 
			@PathVariable Long anoLetivoId,
			@RequestBody SaveFeriadoRequest req ) {	

		try {
			feriadoValidator.validaSaveRequest( req );
			feriadoService.registraFeriado( anoLetivoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('feriadoWRITE')")
	@PutMapping(value="/atualiza/{feriadoId}")
	public ResponseEntity<Object> atualiza(			
			@PathVariable Long feriadoId, 
			@RequestBody SaveFeriadoRequest req ) {	
		
		try {			
			feriadoValidator.validaSaveRequest( req );
			feriadoService.alteraFeriado( feriadoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('feriadoREAD')")
	@GetMapping(value="/get/{feriadoId}")
	public ResponseEntity<Object> busca( @PathVariable Long feriadoId ) {		
		try {
			FeriadoResponse resp = feriadoService.buscaFeriado( feriadoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('feriadoREAD')")
	@PostMapping(value="/filtra/{anoLetivoId}")
	public ResponseEntity<Object> filtra(
			@PathVariable Long anoLetivoId,
			@RequestBody FiltraFeriadoRequest request ) {		
		try {
			List<FeriadoResponse> lista = feriadoService.filtra( anoLetivoId, request );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('feriadoREAD')")
	@GetMapping(value="/lista/{anoLetivoId}")
	public ResponseEntity<Object> lista( @PathVariable Long anoLetivoId ) {		
		try {
			List<FeriadoResponse> lista = feriadoService.lista( anoLetivoId );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('feriadoDELETE')")
	@DeleteMapping(value="/deleta/{feriadoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long feriadoId ) {		
		try {			
			feriadoService.deletaFeriado( feriadoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}

