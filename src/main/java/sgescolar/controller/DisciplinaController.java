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

import sgescolar.model.request.FiltraDisciplinasRequest;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.model.response.DisciplinaResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.DisciplinaService;
import sgescolar.validacao.DisciplinaValidator;

@RestController
@RequestMapping(value="/api/disciplina")
public class DisciplinaController {
	
	@Autowired
	private DisciplinaService disciplinaService;
	
	@Autowired
	private DisciplinaValidator disciplinaValidator;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/registra/{serieId}")
	public ResponseEntity<Object> registraDisciplina( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId,
			@RequestBody SaveDisciplinaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			disciplinaValidator.validaSaveRequest( request );
			disciplinaService.registraDisciplina( serieId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PutMapping(value="/atualiza/{disciplinaId}")
	public ResponseEntity<Object> atualizaDisciplina( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long disciplinaId, 
			@RequestBody SaveDisciplinaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			disciplinaValidator.validaSaveRequest( request );
			disciplinaService.atualizaDisciplina( disciplinaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PostMapping(value="/filtra/{serieId}")
	public ResponseEntity<Object> filtraPorTurma( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId,
			@RequestBody FiltraDisciplinasRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			disciplinaValidator.validaFiltroRequest( request );
			List<DisciplinaResponse> responses = disciplinaService.filtraDisciplinas( serieId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@GetMapping(value="/lista/{serieId}")
	public ResponseEntity<Object> listaPorTurma( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long serieId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			List<DisciplinaResponse> responses = disciplinaService.lista( serieId, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@GetMapping(value="/get/{disciplinaId}")
	public ResponseEntity<Object> getDisciplina(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long disciplinaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			DisciplinaResponse resp = disciplinaService.buscaDisciplina( disciplinaId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
	
	@DeleteMapping(value="/deleta/{disciplinaId}")
	public ResponseEntity<Object> removeDisciplina( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long disciplinaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );			
			disciplinaService.removeDisciplina( disciplinaId, tokenInfos ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );					
		}
	}
		
}

