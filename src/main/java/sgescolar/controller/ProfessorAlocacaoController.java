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
import sgescolar.model.response.ProfessorAlocacaoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ProfessorAlocacaoService;

@RestController
@RequestMapping(value="/api/professor-alocacao")
public class ProfessorAlocacaoController {

	@Autowired
	private ProfessorAlocacaoService professorAlocacaoService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('professorAlocacaoWRITE')" )	
	@PostMapping(value="/registra/{turmaId}/{disciplinaId}/{professorId}") 
	public ResponseEntity<Object> registraProfessorAlocacao( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaId,
			@PathVariable Long disciplinaId,
			@PathVariable Long professorId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			professorAlocacaoService.registraProfessorAlocacao( turmaId, disciplinaId, professorId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('professorAlocacaoREAD')" )	
	@GetMapping(value="/lista/{professorId}") 
	public ResponseEntity<Object> listaProfessorAlocacoes( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long professorId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<ProfessorAlocacaoResponse> lista = professorAlocacaoService.listaVinculos( professorId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('professorAlocacaoREAD')" )	
	@GetMapping(value="/get/{professorAlocacaoId}") 
	public ResponseEntity<Object> getProfessorAlocacao( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long professorAlocacaoId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			ProfessorAlocacaoResponse resp = professorAlocacaoService.getProfessorAlocacao( professorAlocacaoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PreAuthorize("hasAuthority('professorAlocacaoDELETE')" )	
	@DeleteMapping(value="/deleta/porvinculo/{turmaId}/{disciplinaId}/{professorId}") 
	public ResponseEntity<Object> deletaProfessorAlocacao( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaId,
			@PathVariable Long disciplinaId,
			@PathVariable Long professorId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			professorAlocacaoService.deletaProfessorAlocacao( turmaId, disciplinaId, professorId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}
	
	@PreAuthorize("hasAuthority('professorAlocacaoDELETE')" )	
	@DeleteMapping(value="/deleta/{professorAlocacaoId}") 
	public ResponseEntity<Object> deletaProfessorAlocacao( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long professorAlocacaoId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			professorAlocacaoService.deletaProfessorAlocacao( professorAlocacaoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}
	
}
