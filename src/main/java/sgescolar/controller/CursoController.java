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

import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
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
	
	@PreAuthorize("hasAuthority('cursoWRITE')" )	
	@PostMapping(value="/registra/{escolaId}")
	public ResponseEntity<Object> registraCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody SaveCursoRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			cursoValidator.validaSaveRequest( request );
			cursoService.registraCurso( escolaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	

	@PreAuthorize("hasAuthority('cursoWRITE')")
	@PutMapping(value="/atualiza/{cursoId}")
	public ResponseEntity<Object> atualizaCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId, 
			@RequestBody SaveCursoRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			cursoValidator.validaSaveRequest( request );
			cursoService.atualizaCurso( cursoId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	

	@PreAuthorize("hasAuthority('cursoREAD')")
	@PostMapping(value="/filtra/{escolaId}")
	public ResponseEntity<Object> filtraCursos( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody FiltraCursosRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			cursoValidator.validaFiltroRequest( request );
			List<CursoResponse> responses = cursoService.filtraCursos( escolaId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	

	@PreAuthorize("hasAuthority('cursoREAD')")
	@GetMapping(value="/lista/{escolaId}")
	public ResponseEntity<Object> lista( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long escolaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			List<CursoResponse> responses = cursoService.lista( escolaId, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	

	@PreAuthorize("hasAuthority('cursoREAD')")
	@GetMapping(value="/get/{cursoId}")
	public ResponseEntity<Object> getCurso(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			CursoResponse resp = cursoService.buscaCurso( cursoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
	
	@PreAuthorize("hasAuthority('cursoDELETE')")
	@DeleteMapping(value="/deleta/{cursoId}")
	public ResponseEntity<Object> removeCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			cursoService.removeCurso( cursoId, tokenInfos ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
		
}
