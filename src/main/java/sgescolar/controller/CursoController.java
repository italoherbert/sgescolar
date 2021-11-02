package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.service.CursoService;
import sgescolar.validacao.CursoValidator;

@RestController
@RequestMapping(value="/api/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoValidator cursoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraCurso( 
			@RequestHeader("Authorization") String auth, 
			@RequestBody SaveCursoRequest request ) {
		
		try {
			Long logadoEID = jwtTokenUtil.getEID( auth );			
			cursoValidator.validaSaveRequest( request );
			cursoService.registraCurso( logadoEID, request );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PutMapping(value="/atualiza/{cursoId}")
	public ResponseEntity<Object> atualizaCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId, @RequestBody SaveCursoRequest request ) {
		
		try {
			Long logadoEID = jwtTokenUtil.getEID( auth );			
			cursoValidator.validaSaveRequest( request );
			cursoService.atualizaCurso( logadoEID, cursoId, request );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=CursoResponse.class)))),	
	} )
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtraCursos( 
			@RequestHeader("Authorization") String auth,
			@RequestBody FiltraCursosRequest request ) {
		
		try {
			cursoValidator.validaFiltroRequest( request );
			Long logadoEID = jwtTokenUtil.getEID( auth ); 			
			List<CursoResponse> responses = cursoService.filtraCursos( logadoEID, request );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = CursoResponse.class))),	
	} )
	@GetMapping(value="/get/{cursoId}")
	public ResponseEntity<Object> getCurso(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			Long logadoEID = jwtTokenUtil.getEID( auth );								
			CursoResponse resp = cursoService.buscaCurso( logadoEID, cursoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@DeleteMapping(value="/deleta/{cursoId}")
	public ResponseEntity<Object> removeCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			Long logadoEID = jwtTokenUtil.getEID( auth );		
			cursoService.removeCurso( logadoEID, cursoId ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
		
}
