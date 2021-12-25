package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveAvaliacaoExternaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.AvaliacaoExternaService;
import sgescolar.service.ServiceException;

@RestController
@RequestMapping( value="/api/avaliacao/externa")
public class AvaliacaoExternaController {
	
	@Autowired
	private AvaliacaoExternaService avaliacaoExternaService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('avaliacaoExternaWRITE')")
	@PostMapping(value="/registra/{alunoId}/{turmaDisciplinaId}")
	public ResponseEntity<Object> registraAvaliacaoExterna(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long alunoId,
			@PathVariable Long turmaDisciplinaId, 
			@RequestBody SaveAvaliacaoExternaRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth ); 
			avaliacaoExternaService.registraAvaliacaoExternaAnoAtual( alunoId, turmaDisciplinaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( ServiceException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoExternaDELETE')")
	@DeleteMapping(value="/deleta/{alunoId}/{turmaDisciplinaId}")
	public ResponseEntity<Object> deletaAvaliacaoExterna(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long alunoId,
			@PathVariable Long turmaDisciplinaId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth ); 
			avaliacaoExternaService.removeAvaliacaoExternaAnoAtual( alunoId, turmaDisciplinaId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( ServiceException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}

}
