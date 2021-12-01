package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.BuscaListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.ListaAlunoFrequenciaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ListaAlunoFrequenciaService;
import sgescolar.service.ServiceException;
import sgescolar.validacao.ListaAlunoFrequenciaValidator;

@RestController
@RequestMapping(value="/api/lista-frequencia/")
public class ListaAlunoFrequenciaController {

	@Autowired
	private ListaAlunoFrequenciaService lafService;
	
	@Autowired
	private ListaAlunoFrequenciaValidator lafValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/salva/{turmaId}")
	public ResponseEntity<Object> salvaHorario(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId, 
			@RequestBody SaveListaAlunoFrequenciaRequest request ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			lafValidator.validaSaveRequest( request );
			lafService.salvaLAF( turmaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PostMapping(value="/busca/{turmaId}")
	public ResponseEntity<Object> buscaLAF(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId, 
			@RequestBody BuscaListaAlunoFrequenciaRequest request ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			lafValidator.validaBuscaRequest( request );
			ListaAlunoFrequenciaResponse resp = lafService.buscaLAF( turmaId, request, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@GetMapping(value="/busca/{lafId}")
	public ResponseEntity<Object> buscaLAF(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long lafId ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			ListaAlunoFrequenciaResponse resp = lafService.buscaLAF( lafId, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@DeleteMapping(value="/deleta/{lafId}")
	public ResponseEntity<Object> deletaLAF(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long lafId ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth ); 
			lafService.deletaLAF( lafId, tokenInfos );
			return ResponseEntity.ok().build(); 
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	
}

