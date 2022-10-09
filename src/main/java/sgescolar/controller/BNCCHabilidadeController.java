package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import sgescolar.model.request.SaveBNCCHabilidadeRequest;
import sgescolar.model.request.filtro.FiltraBNCCHabilidadesRequest;
import sgescolar.model.response.BNCCHabilidadeResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.BNCCHabilidadeService;
import sgescolar.validacao.BNCCHabilidadeValidator;

@RestController
@RequestMapping(value="/api/bncc-habilidade") 
public class BNCCHabilidadeController {

	@Autowired
	private BNCCHabilidadeService bnccHabilidadeService;
		
	@Autowired
	private BNCCHabilidadeValidator bnccHabilidadeValidator;
	
	@PreAuthorize("hasAuthority('bnccHabilidadeWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveBNCCHabilidadeRequest req ) {		
		try {
			bnccHabilidadeValidator.validaSaveRequest( req );
			bnccHabilidadeService.registraBNCCHabilidade( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('bnccHabilidadeWRITE')")
	@PutMapping(value="/atualiza/{codigo}")
	public ResponseEntity<Object> atualiza( @PathVariable String codigo, @RequestBody SaveBNCCHabilidadeRequest req ) {		
		try {
			bnccHabilidadeValidator.validaSaveRequest( req );
			bnccHabilidadeService.alteraBNCCHabilidade( codigo, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('bnccHabilidadeREAD')")
	@PostMapping(value="/filtra/limit/{limit}")
	public ResponseEntity<Object> filtra( @PathVariable int limit, @RequestBody FiltraBNCCHabilidadesRequest request ) {
		try {
			Pageable p = PageRequest.of( 0, limit );
			bnccHabilidadeValidator.validaFiltroRequest( request );			
			List<BNCCHabilidadeResponse> lista = bnccHabilidadeService.filtraBNCCHabilidades( request, p );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
		
	@PreAuthorize("hasAuthority('bnccHabilidadeREAD')")
	@GetMapping(value="/get/{codigo}")
	public ResponseEntity<Object> busca( @PathVariable String codigo ) {				
		try {
			BNCCHabilidadeResponse resp = bnccHabilidadeService.buscaBNCCHabilidade( codigo );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('bnccHabilidadeDELETE')")
	@DeleteMapping(value="/deleta/{codigo}")
	public ResponseEntity<Object> deleta( @PathVariable String codigo ) {
		try {
			bnccHabilidadeService.deletaBNCCHabilidade( codigo );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}

