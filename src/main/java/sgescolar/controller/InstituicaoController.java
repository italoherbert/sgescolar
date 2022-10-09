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

import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.model.request.filtro.FiltraInstituicaoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.InstituicaoService;
import sgescolar.validacao.InstituicaoValidator;

@RestController
@RequestMapping(value="/api/instituicao") 
public class InstituicaoController {

	@Autowired
	private InstituicaoService instituicaoService;
	
	@Autowired
	private InstituicaoValidator instituicaoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
			
	@PreAuthorize("hasAuthority('instituicaoWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveInstituicaoRequest req ) {
		try {
			instituicaoValidator.validaSaveRequest( req );
			instituicaoService.registraInstituicao( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoWRITE')")
	@PutMapping(value="/atualiza/{instituicaoId}")
	public ResponseEntity<Object> atualiza(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long instituicaoId, 
			@RequestBody SaveInstituicaoRequest req ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			instituicaoValidator.validaSaveRequest( req );
			instituicaoService.alteraInstituicao( instituicaoId, req, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoREAD')")
	@GetMapping(value="/lista")
	public ResponseEntity<Object> lista( 
			@RequestHeader( "Authorization" ) String auth ) {			
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<InstituicaoResponse> resps = instituicaoService.listaInstituicoes( tokenInfos );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( 
			@RequestHeader( "Authorization" ) String auth,
			@RequestBody FiltraInstituicaoRequest request ) {			
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<InstituicaoResponse> resps = instituicaoService.filtraInstituicoes( request, tokenInfos );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoREAD')")
	@GetMapping(value="/get/{instituicaoId}")
	public ResponseEntity<Object> busca( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long instituicaoId ) {			
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			InstituicaoResponse resp = instituicaoService.buscaInstituicao( instituicaoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoDELETE')")
	@DeleteMapping(value="/deleta/{instituicaoId}")
	public ResponseEntity<Object> deleta( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long instituicaoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			instituicaoService.deletaInstituicao( instituicaoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}


