package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.FiltraMatriculaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.MatriculaService;
import sgescolar.validacao.MatriculaValidator;

@RestController
@RequestMapping(value="/api/matricula")
public class MatriculaController {
	
	@Autowired
	private MatriculaService matriculaService;
	
	@Autowired
	private MatriculaValidator matriculaValidator;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('matriculaWRITE')" )	
	@PostMapping(value="/registra/{alunoId}/{turmaId}")
	public ResponseEntity<Object> registraMatricula( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long alunoId, 
			@PathVariable Long turmaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			matriculaService.registraMatricula( alunoId, turmaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
		
	@PreAuthorize("hasAuthority('matriculaREAD')" )	
	@GetMapping(value="/lista/{alunoId}")
	public ResponseEntity<Object> listaMatriculas( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long alunoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<MatriculaResponse> lista = matriculaService.listaMatriculasPorAlunoID( alunoId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('matriculaREAD')" )	
	@PostMapping(value="/filtra/{turmaId}")
	public ResponseEntity<Object> listaPorAnoETurma( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId,
			@RequestBody FiltraMatriculaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			matriculaValidator.validaBuscaRequest( request );
			List<MatriculaResponse> lista = matriculaService.filtra( turmaId, request, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
	@PreAuthorize("hasAuthority('matriculaREAD')" )	
	@GetMapping(value="/get/{matriculaId}")
	public ResponseEntity<Object> buscaMatriculaPorId( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long matriculaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			MatriculaResponse resp = matriculaService.buscaMatricula( matriculaId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('matriculaREAD')" )	
	@GetMapping(value="/get/pornumero/{numero}")
	public ResponseEntity<Object> buscaMatriculaPorNumero( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable String numero ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			MatriculaResponse resp = matriculaService.buscaMatriculaPorNumero( numero, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('matriculaDELETE')" )	
	@DeleteMapping(value="/deleta/{matriculaId}")
	public ResponseEntity<Object> deletaMatricula( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long matriculaId ) {
	
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			matriculaService.deletaMatricula( matriculaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
