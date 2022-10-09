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

import sgescolar.model.request.SaveRecursoRequest;
import sgescolar.model.request.filtro.FiltraRecursosRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.RecursoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.RecursoService;
import sgescolar.validacao.RecursoValidator;

@RestController
@RequestMapping(value="/api/recurso") 
public class RecursoController {

	@Autowired
	private RecursoService recursoService;
		
	@Autowired
	private RecursoValidator recursoValidator;
	
	@PreAuthorize("hasAuthority('recursoWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveRecursoRequest req ) {		
		try {
			recursoValidator.validaSaveRequest( req );
			recursoService.registraRecurso( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('recursoWRITE')")
	@PutMapping(value="/atualiza/{recursoId}")
	public ResponseEntity<Object> atualiza( @PathVariable Long recursoId, @RequestBody SaveRecursoRequest req ) {		
		try {
			recursoValidator.validaSaveRequest( req );
			recursoService.alteraRecurso( recursoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('recursoREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( @RequestBody FiltraRecursosRequest request ) {
		try {
			recursoValidator.validaFiltroRequest( request );			
			List<RecursoResponse> lista = recursoService.filtraRecursos( request );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('recursoREAD')")
	@GetMapping(value="/lista")
	public ResponseEntity<Object> lista() {
		List<RecursoResponse> lista = recursoService.listaRecursos();
		return ResponseEntity.ok( lista );			
	}
	
	@PreAuthorize("hasAuthority('recursoREAD')")
	@GetMapping(value="/get/{recursoId}")
	public ResponseEntity<Object> busca( @PathVariable Long recursoId ) {				
		try {
			RecursoResponse resp = recursoService.buscaRecurso( recursoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('recursoDELETE')")
	@DeleteMapping(value="/deleta/{recursoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long recursoId ) {
		try {
			recursoService.deletaRecurso( recursoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}
