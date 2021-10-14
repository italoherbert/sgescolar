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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sgescolar.exception.EscolaJaExisteException;
import sgescolar.exception.EscolaNaoEncontradaException;
import sgescolar.model.request.EscolaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.EscolaResponse;
import sgescolar.service.EscolaService;

@RestController
@RequestMapping(value="/api/escola")
public class EscolaController {
	
	@Autowired
	private EscolaService escolaService;
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraEscola( @RequestBody EscolaRequest request ) {
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_ESCOLA_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_ESCOLA_OBRIGATORIO ) );
		
		try {
			escolaService.registraEscola( request );
			return ResponseEntity.ok().build();
		} catch ( EscolaJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.ESCOLA_JA_EXISTE ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PutMapping(value="/atualiza/{escolaId}")
	public ResponseEntity<Object> atualizaEscola( @PathVariable Long escolaId, @RequestBody EscolaRequest request ) {
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_ESCOLA_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_ESCOLA_OBRIGATORIO ) );
		
		try {
			escolaService.atualizaEscola( escolaId, request );
			return ResponseEntity.ok().build();
		} catch ( EscolaNaoEncontradaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.ESCOLA_NAO_ENCONTRADA ) );
		} catch ( EscolaJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.ESCOLA_JA_EXISTE ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=EscolaResponse.class)))),	
	} )
	@GetMapping(value="/filtra/{nomeIni}")
	public ResponseEntity<Object> filtraEscolas( @PathVariable String nomeIni ) {						
		List<EscolaResponse> responses = escolaService.filtraEscolasPorNomeIni( nomeIni );
		return ResponseEntity.ok( responses );
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = EscolaResponse.class))),	
	} )
	@GetMapping(value="/get/{escolaId}")
	public ResponseEntity<Object> getEscola( @PathVariable Long escolaId ) {
		try {
			EscolaResponse resp = escolaService.buscaEscola( escolaId );
			return ResponseEntity.ok( resp );
		} catch (EscolaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.ESCOLA_NAO_ENCONTRADA ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@DeleteMapping(value="/deleta/{escolaId}")
	public ResponseEntity<Object> removeEscola( @PathVariable Long escolaId ) {
		try {
			escolaService.removeEscola( escolaId ); 
			return ResponseEntity.ok().build();
		} catch (EscolaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.ESCOLA_NAO_ENCONTRADA ) );
		}
	}
		
}
