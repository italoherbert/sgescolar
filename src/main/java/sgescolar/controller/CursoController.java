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
import sgescolar.exception.CursoJaExisteException;
import sgescolar.exception.CursoModalidadeNaoReconhecidaException;
import sgescolar.exception.CursoNaoEncontradoException;
import sgescolar.exception.SemPrivilegioPorEscopoEscolaException;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.response.CursoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.service.CursoService;
import sgescolar.util.jwt.JwtTokenUtil;
import sgescolar.util.jwt.TokenInfos;

@RestController
@RequestMapping(value="/api/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraCurso( 
			@RequestHeader("Authorization") String auth, @RequestBody SaveCursoRequest request ) {

		Long logadoEID = tokenUtil.getBearerTokenInfos( auth ).getLogadoEID();
		if ( logadoEID == TokenInfos.ID_NAO_EXTRAIDO )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EID_NAO_EXTRAIDO_DE_TOKEN ) );		
		
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_CURSO_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_CURSO_OBRIGATORIO ) );
		
		try {
			cursoService.registraCurso( logadoEID, request );
			return ResponseEntity.ok().build();
		} catch ( CursoJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_JA_EXISTE ) );
		} catch (CursoModalidadeNaoReconhecidaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_MODALIDADE_NAO_RECONHECIDA ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PutMapping(value="/atualiza/{cursoId}")
	public ResponseEntity<Object> atualizaCurso( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId, @RequestBody SaveCursoRequest request ) {
		
		Long logadoEID = tokenUtil.getBearerTokenInfos( auth ).getLogadoEID();
		if ( logadoEID == TokenInfos.ID_NAO_EXTRAIDO )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EID_NAO_EXTRAIDO_DE_TOKEN ) );
		
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_CURSO_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_CURSO_OBRIGATORIO ) );
				
		try {						
			cursoService.atualizaCurso( logadoEID, cursoId, request );
			return ResponseEntity.ok().build();
		} catch ( CursoNaoEncontradoException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_NAO_ENCONTRADO ) );
		} catch ( CursoJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_JA_EXISTE ) );
		} catch (CursoModalidadeNaoReconhecidaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_MODALIDADE_NAO_RECONHECIDA ) );
		} catch (SemPrivilegioPorEscopoEscolaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.SEM_PERMISSAO_POR_ESCOPO_ESCOLA ) );			
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=CursoResponse.class)))),	
	} )
	@GetMapping(value="/filtra/{nomeIni}")
	public ResponseEntity<Object> filtraCursos( 
			@RequestHeader("Authorization") String auth,
			@PathVariable String nomeIni ) {
		
		Long logadoEID = tokenUtil.getBearerTokenInfos( auth ).getLogadoEID();
		if ( logadoEID == TokenInfos.ID_NAO_EXTRAIDO )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EID_NAO_EXTRAIDO_DE_TOKEN ) );		
		
		List<CursoResponse> responses = cursoService.filtraCursos( logadoEID, nomeIni );
		return ResponseEntity.ok( responses );
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = CursoResponse.class))),	
	} )
	@GetMapping(value="/get/{cursoId}")
	public ResponseEntity<Object> getCurso(
			@RequestHeader("Authorization") String auth,
			@PathVariable Long cursoId ) {
		
		try {
			Long logadoEID = tokenUtil.getBearerTokenInfos( auth ).getLogadoEID();
			if ( logadoEID == TokenInfos.ID_NAO_EXTRAIDO )
				return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EID_NAO_EXTRAIDO_DE_TOKEN ) );					
			
			CursoResponse resp = cursoService.buscaCurso( logadoEID, cursoId );
			return ResponseEntity.ok( resp );
		} catch (CursoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_NAO_ENCONTRADO ) );
		} catch (SemPrivilegioPorEscopoEscolaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.SEM_PERMISSAO_POR_ESCOPO_ESCOLA ) );					
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
			Long logadoEID = tokenUtil.getBearerTokenInfos( auth ).getLogadoEID();
			if ( logadoEID == TokenInfos.ID_NAO_EXTRAIDO )
				return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EID_NAO_EXTRAIDO_DE_TOKEN ) );					
			
			cursoService.removeCurso( logadoEID, cursoId ); 
			return ResponseEntity.ok().build();
		} catch (CursoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.CURSO_NAO_ENCONTRADO ) );
		} catch (SemPrivilegioPorEscopoEscolaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.SEM_PERMISSAO_POR_ESCOPO_ESCOLA ) );					
		}
	}
		
}
