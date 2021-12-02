package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.MatriculaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.MatriculaService;

@RestController
@RequestMapping(value="/api/matricula")
public class MatriculaController {
	
	@Autowired
	private MatriculaService matriculaService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
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
	
	@GetMapping(value="/lista/porturma/{turmaId}")
	public ResponseEntity<Object> listaMatriculasPorTurma( 
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<MatriculaResponse> lista = matriculaService.listaMatriculasPorTurmaID( turmaId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
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
