package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAuthority('turmaWRITE')" )	
	@PostMapping(value="/sincroniza/{turmaId}")
	public ResponseEntity<Object> sincronizaVinculos(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			turmaDisciplinaService.sincronizaVinculoTurmaDisciplinas( turmaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}	
	}
	
	@PreAuthorize("hasAuthority('turmaWRITE')" )	
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
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/porturma/{turmaId}") 
	public ResponseEntity<Object> listaTurmaDisciplinasPorTurma( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<TurmaDisciplinaResponse> lista = turmaDisciplinaService.listaPorTurma( turmaId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/porturma/porprof/{turmaId}/{professorId}") 
	public ResponseEntity<Object> listaTurmaDisciplinasPorTurmaEProfessor(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId, 
			@PathVariable Long professorId ) {
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<TurmaDisciplinaResponse> lista = turmaDisciplinaService.listaPorTurmaEProfessor( turmaId, professorId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/porprof/{professorId}") 
	public ResponseEntity<Object> listaTurmaDisciplinasPorProfessor( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long professorId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<TurmaDisciplinaResponse> lista = turmaDisciplinaService.listaPorProfessor( professorId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/lista/poraluno/{alunoId}") 
	public ResponseEntity<Object> listaTurmaDisciplinasPorAluno( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long alunoId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<TurmaDisciplinaResponse> lista = turmaDisciplinaService.listaPorAluno( alunoId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('turmaREAD')" )	
	@GetMapping(value="/get/{turmaDisciplinaId}") 
	public ResponseEntity<Object> getTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaDisciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			TurmaDisciplinaResponse resp = turmaDisciplinaService.getTurmaDisciplina( turmaDisciplinaId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('turmaDELETE')" )	
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
	
	@PreAuthorize("hasAuthority('turmaDELETE')" )	
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
