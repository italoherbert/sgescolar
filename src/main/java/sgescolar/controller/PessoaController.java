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
import sgescolar.exception.PessoaJaExisteException;
import sgescolar.exception.PessoaNaoEncontradaException;
import sgescolar.model.request.PessoaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.PessoaResponse;
import sgescolar.service.PessoaService;

@RestController
@RequestMapping(value="/api/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraPessoa( @RequestBody PessoaRequest request ) {
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_PESSOA_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_PESSOA_OBRIGATORIO ) );
		
		if ( request.getEmail() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EMAIL_PESSOA_OBRIGATORIO ) );
		if ( request.getEmail().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EMAIL_PESSOA_OBRIGATORIO ) );
		
		try {
			pessoaService.registraPessoa( request );
			return ResponseEntity.ok().build();
		} catch ( PessoaJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PESSOA_JA_EXISTE ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PutMapping(value="/atualiza/{pessoaId}")
	public ResponseEntity<Object> atualizaPessoa( @PathVariable Long pessoaId, @RequestBody PessoaRequest request ) {
		if ( request.getNome() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_PESSOA_OBRIGATORIO ) );
		if ( request.getNome().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.NOME_PESSOA_OBRIGATORIO ) );
		
		if ( request.getEmail() == null )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EMAIL_PESSOA_OBRIGATORIO ) );
		if ( request.getEmail().isBlank() )
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.EMAIL_PESSOA_OBRIGATORIO ) );
		
		try {
			pessoaService.atualizaPessoa( pessoaId, request );
			return ResponseEntity.ok().build();
		} catch ( PessoaNaoEncontradaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PESSOA_NAO_ENCONTRADA ) );
		} catch ( PessoaJaExisteException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PESSOA_JA_EXISTE ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=PessoaResponse.class)))),	
	} )
	@GetMapping(value="/filtra/{nomeIni}")
	public ResponseEntity<Object> filtraPessoas( @PathVariable String nomeIni ) {						
		List<PessoaResponse> responses = pessoaService.filtraPessoasPorNomeIni( nomeIni );
		return ResponseEntity.ok( responses );
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = PessoaResponse.class))),	
	} )
	@GetMapping(value="/get/{pessoaId}")
	public ResponseEntity<Object> getPessoa( @PathVariable Long pessoaId ) {
		try {
			PessoaResponse resp = pessoaService.buscaPessoa( pessoaId );
			return ResponseEntity.ok( resp );
		} catch (PessoaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PESSOA_NAO_ENCONTRADA ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@DeleteMapping(value="/deleta/{pessoaId}")
	public ResponseEntity<Object> removePessoa( @PathVariable Long pessoaId ) {
		try {
			pessoaService.removePessoa( pessoaId ); 
			return ResponseEntity.ok().build();
		} catch (PessoaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( ErroResponse.PESSOA_NAO_ENCONTRADA ) );
		}
	}
		
}
