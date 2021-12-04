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

import sgescolar.model.request.FiltraEscolasRequest;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.EscolaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.EscolaService;
import sgescolar.validacao.EscolaValidator;

@RestController
@RequestMapping(value="/api/escola")
public class EscolaController {
	
	@Autowired
	private EscolaService escolaService;
		
	@Autowired
	private EscolaValidator escolaValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
			
	@PreAuthorize("hasAuthority('escolaWRITE')" )	
	@PostMapping(value="/registra/{instituicaoId}")
	public ResponseEntity<Object> registraEscola(
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long instituicaoId, 
			@RequestBody SaveEscolaRequest request ) {
		
		try {			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			escolaValidator.validaSaveRequest( request ); 
			escolaService.registraEscola( instituicaoId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('escolaWRITE')" )	
	@PutMapping(value="/atualiza/{escolaId}")
	public ResponseEntity<Object> atualizaEscola( 
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long escolaId,
			@RequestBody SaveEscolaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			escolaValidator.validaSaveRequest( request );
			escolaService.atualizaEscola( escolaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('escolaREAD')" )	
	@GetMapping(value="/lista/{instituicaoId}")
	public ResponseEntity<Object> listaEscolas(			
			@RequestHeader("Authorization") String auth,
			@PathVariable Long instituicaoId ) {
		
		TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );		
		List<EscolaResponse> responses = escolaService.listaEscolas( instituicaoId, tokenInfos  );
		return ResponseEntity.ok( responses );		
	}	
	
	@PreAuthorize("hasAuthority('escolaREAD')" )	
	@PostMapping(value="/filtra/{instituicaoId}")
	public ResponseEntity<Object> filtraEscolas(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long instituicaoId,
			@RequestBody FiltraEscolasRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			
			escolaValidator.validaFiltroRequest( request );
			List<EscolaResponse> responses = escolaService.filtraEscolas( instituicaoId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}	
	
	@PreAuthorize("hasAuthority('escolaREAD')" )	
	@GetMapping(value="/get/{escolaId}")
	public ResponseEntity<Object> getEscola( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long escolaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			EscolaResponse resp = escolaService.buscaEscola( escolaId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('escolaDELETE')" )	
	@DeleteMapping(value="/deleta/{escolaId}")
	public ResponseEntity<Object> removeEscola( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long escolaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			escolaService.removeEscola( escolaId, tokenInfos ); 
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
