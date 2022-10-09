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

import sgescolar.model.request.SaveSerieRequest;
import sgescolar.model.request.filtro.FiltraSeriesRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.SerieResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.SerieService;
import sgescolar.validacao.SerieValidator;

@RestController
@RequestMapping(value="/api/serie")
public class SerieController {
	
	@Autowired
	private SerieService serieService;
	
	@Autowired
	private SerieValidator serieValidator;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('serieWRITE')" )	
	@PostMapping(value="/registra/{cursoId}")
	public ResponseEntity<Object> registraSerie( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId,
			@RequestBody SaveSerieRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			serieValidator.validaSaveRequest( request );
			serieService.registraSerie( cursoId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('serieWRITE')" )	
	@PutMapping(value="/atualiza/{serieId}")
	public ResponseEntity<Object> atualizaSerie( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId, 
			@RequestBody SaveSerieRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			serieValidator.validaSaveRequest( request );
			serieService.atualizaSerie( serieId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('serieREAD')" )	
	@PostMapping(value="/filtra/{cursoId}")
	public ResponseEntity<Object> filtraSeries( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId,
			@RequestBody FiltraSeriesRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			serieValidator.validaFiltroRequest( request );
			List<SerieResponse> responses = serieService.filtraSeries( cursoId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('serieREAD')" )	
	@GetMapping(value="/lista/{cursoId}")
	public ResponseEntity<Object> listaSeries( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			List<SerieResponse> responses = serieService.listaSeries( cursoId, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('serieREAD')" )	
	@GetMapping(value="/get/{serieId}")
	public ResponseEntity<Object> getSerie(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			SerieResponse resp = serieService.buscaSerie( serieId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
	
	@PreAuthorize("hasAuthority('serieDELETE')" )	
	@DeleteMapping(value="/deleta/{serieId}")
	public ResponseEntity<Object> removeSerie( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			serieService.removeSerie( serieId, tokenInfos ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
		
}

