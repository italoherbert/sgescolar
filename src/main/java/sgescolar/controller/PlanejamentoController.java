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

import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.model.request.filtro.FiltraPlanejamentosRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.PlanejamentoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.PlanejamentoService;
import sgescolar.validacao.PlanejamentoValidator;

@RestController
@RequestMapping(value="/api/planejamento")
public class PlanejamentoController {
	
	@Autowired
	private PlanejamentoService planejamentoService;
	
	@Autowired
	private PlanejamentoValidator planejamentoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PreAuthorize("hasAuthority('planejamentoWRITE')")
	@PostMapping(value="/registra/{professorAlocacaoId}")
	public ResponseEntity<Object> registraPlanejamento( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long professorAlocacaoId,
			@RequestBody SavePlanejamentoRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			planejamentoValidator.validaSaveRequest( req );
			planejamentoService.registraPlanejamento( professorAlocacaoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('planejamentoWRITE')")
	@PutMapping(value="/atualiza/{planejamentoId}")
	public ResponseEntity<Object> atualizaPlanejamento(  
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long planejamentoId, 
			@RequestBody SavePlanejamentoRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			planejamentoValidator.validaSaveRequest( req );
			planejamentoService.alteraPlanejamento( planejamentoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('planejamentoREAD')")
	@PostMapping(value="/filtra/{professorAlocacaoId}")
	public ResponseEntity<Object> filtraPlanejamentos(   
			@RequestHeader( "Authorization" ) String auth, 
			@PathVariable Long professorAlocacaoId,
			@RequestBody FiltraPlanejamentosRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			planejamentoValidator.validaFiltroRequest( request );
			List<PlanejamentoResponse> lista = planejamentoService.filtraPlanejamentos( professorAlocacaoId, request, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('planejamentoREAD')")
	@GetMapping(value="/lista/{professorAlocacaoId}")
	public ResponseEntity<Object> listaPlanejamentos(   
			@RequestHeader( "Authorization" ) String auth, 
			@PathVariable Long professorAlocacaoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<PlanejamentoResponse> lista = planejamentoService.listaPlanejamentos( professorAlocacaoId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('planejamentoREAD')")
	@GetMapping(value="/lista/plan-ensino/{professorAlocacaoId}")
	public ResponseEntity<Object> listaEnsinoPlanejamentos(   
			@RequestHeader( "Authorization" ) String auth, 
			@PathVariable Long professorAlocacaoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<PlanejamentoResponse> lista = planejamentoService.listaEnsinoPlanejamentos( professorAlocacaoId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('planejamentoREAD')")
	@GetMapping(value="/get/{planejamentoId}")
	public ResponseEntity<Object> buscaPlanejamento(   
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long planejamentoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			PlanejamentoResponse resp = planejamentoService.getPlanejamento( planejamentoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('planejamentoDELETE')")
	@DeleteMapping(value="/deleta/{planejamentoId}")
	public ResponseEntity<Object> deletaPlanejamento(    
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long planejamentoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			planejamentoService.deletaPlanejamento( planejamentoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}	
	
}
