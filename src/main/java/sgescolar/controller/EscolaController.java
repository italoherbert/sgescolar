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
import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.EscolaResponse;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.EscolaService;
import sgescolar.service.InstituicaoService;
import sgescolar.validacao.EscolaValidator;

@RestController
@RequestMapping(value="/api/escola")
public class EscolaController {
	
	@Autowired
	private EscolaService escolaService;
	
	@Autowired
	private InstituicaoService instituicaoService;
	
	@Autowired
	private EscolaValidator escolaValidator;
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraEscola( @RequestBody SaveEscolaRequest request ) {		
		try {
			InstituicaoResponse inst = instituicaoService.buscaInstituicao();
			Long instId = inst.getId();
			
			escolaValidator.validaSaveRequest( request ); 
			escolaService.registraEscola( instId, request );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = Object.class))),	
	} )
	@PutMapping(value="/atualiza/{escolaId}")
	public ResponseEntity<Object> atualizaEscola( @PathVariable Long escolaId, @RequestBody SaveEscolaRequest request ) {		
		try {
			escolaValidator.validaSaveRequest( request );
			escolaService.atualizaEscola( escolaId, request );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=EscolaResponse.class)))),	
	} )
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtraEscolas( @RequestBody FiltraEscolasRequest request ) {
		try {
			escolaValidator.validaFiltroRequest( request );
			List<EscolaResponse> responses = escolaService.filtraEscolas( request );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}		
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content=@Content(schema = @Schema(implementation = EscolaResponse.class))),	
	} )
	@GetMapping(value="/get/{escolaId}")
	public ResponseEntity<Object> getEscola( @PathVariable Long escolaId ) {
		try {
			EscolaResponse resp = escolaService.buscaEscola( escolaId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
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
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
