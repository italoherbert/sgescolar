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
import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.EscolaResponse;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.EscolaService;
import sgescolar.service.InstituicaoService;
import sgescolar.service.filtra.FiltroEscolas;
import sgescolar.service.filtra.FiltroManager;
import sgescolar.service.lista.ListaEscolas;
import sgescolar.service.lista.ListaManager;
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
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private ListaManager listaManager;
	
	@Autowired
	private FiltroManager filtroManager;
		
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
	@GetMapping(value="/lista")
	public ResponseEntity<Object> listaEscolas(
			@RequestHeader("Authorization") String auth ) {
		
		TokenInfos infos = jwtTokenUtil.getBearerTokenInfos( auth );
		
		ListaEscolas listaEscolas = listaManager.novoListaEscolas( infos );
		List<EscolaResponse> responses = escolaService.listaEscolas( listaEscolas );
		return ResponseEntity.ok( responses );		
	}	
	
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation=EscolaResponse.class)))),	
	} )
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtraEscolas(
			@RequestHeader( "Authorization" ) String auth,
			@RequestBody FiltraEscolasRequest request ) {
		
		try {
			TokenInfos infos = jwtTokenUtil.getBearerTokenInfos( auth );
			FiltroEscolas filtroEscolas = filtroManager.novoFiltroEscolas( infos );
			
			escolaValidator.validaFiltroRequest( request );
			List<EscolaResponse> responses = escolaService.filtraEscolas( filtroEscolas, request );
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
