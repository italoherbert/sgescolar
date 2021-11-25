package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.TurmaDisciplinaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.TurmaDisciplinaService;

@RestController
@RequestMapping(value="/api/turma-disciplina")
public class TurmaDisciplinaController {

	@Autowired
	private TurmaDisciplinaService turmaDisciplinaService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/registra/{turmaId}/{disciplinaId}") 
	public ResponseEntity<Object> registraTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaId,
			@PathVariable Long disciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			turmaDisciplinaService.registraTurmaDisciplina( turmaId, disciplinaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@GetMapping(value="/lista/porturma/{turmaId}") 
	public ResponseEntity<Object> listaTurmaDisciplinasPorTurma( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<TurmaDisciplinaResponse> lista = turmaDisciplinaService.listaVinculosPorTurma( turmaId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@GetMapping(value="/get/{turmaDisciplinaId}") 
	public ResponseEntity<Object> getTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaDisciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			turmaDisciplinaService.getTurmaDisciplina( turmaDisciplinaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@DeleteMapping(value="/deleta/porvinculo/{turmaId}/{disciplinaId}") 
	public ResponseEntity<Object> deletaTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaId,
			@PathVariable Long disciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			turmaDisciplinaService.deletaTurmaDisciplina( turmaId, disciplinaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}
	
	@DeleteMapping(value="/deleta/{turmaDisciplinaId}") 
	public ResponseEntity<Object> deletaTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaDisciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			turmaDisciplinaService.deletaTurmaDisciplina( turmaDisciplinaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}
	
}
