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

import sgescolar.model.request.FiltraTurmasRequest;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.TurmaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.TurmaService;
import sgescolar.validacao.TurmaValidator;

@RestController
@RequestMapping(value="/api/turma")
public class TurmaController {
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private TurmaValidator turmaValidator;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('turmaWRITE')" )	
	@PostMapping(value="/registra/{serieId}/{anoLetivoId}")
	public ResponseEntity<Object> registraTurma( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId,
			@PathVariable Long anoLetivoId,
			@RequestBody SaveTurmaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			turmaValidator.validaSaveRequest( request );
			turmaService.registraTurma( serieId, anoLetivoId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaWRITE')" )	
	@PutMapping(value="/atualiza/{turmaId}")
	public ResponseEntity<Object> atualizaTurma( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaId, 
			@RequestBody SaveTurmaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			turmaValidator.validaSaveRequest( request );
			turmaService.atualizaTurma( turmaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@PostMapping(value="/filtra/porserie/{serieId}")
	public ResponseEntity<Object> filtraPorSerie( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId,
			@RequestBody FiltraTurmasRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			turmaValidator.validaFiltroRequest( request );
			List<TurmaResponse> responses = turmaService.filtraTurmasPorSerie( serieId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/porserie/{serieId}")
	public ResponseEntity<Object> listaPorSerie( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			List<TurmaResponse> responses = turmaService.listaTurmasPorSerie( serieId, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@PostMapping(value="/filtra/poranoletivo/{anoLetivoId}")
	public ResponseEntity<Object> filtraPorAnoLetivo( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId,
			@RequestBody FiltraTurmasRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			turmaValidator.validaFiltroRequest( request );
			List<TurmaResponse> responses = turmaService.filtraTurmasPorAnoLetivo( anoLetivoId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/poranoletivo/{anoLetivoId}")
	public ResponseEntity<Object> listaPorAnoLetivo( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long anoLetivoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			List<TurmaResponse> responses = turmaService.listaTurmasPorAnoLetivo( anoLetivoId, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/get/{turmaId}")
	public ResponseEntity<Object> getTurma(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			TurmaResponse resp = turmaService.buscaTurma( turmaId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
	
	@PreAuthorize("hasAuthority('turmaDELETE')" )	
	@DeleteMapping(value="/deleta/{turmaId}")
	public ResponseEntity<Object> removeTurma( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			turmaService.removeTurma( turmaId, tokenInfos ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
		
}
