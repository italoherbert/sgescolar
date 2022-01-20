package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.BoletimResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.BoletimService;

@RestController
@RequestMapping(value="/api/boletim")
public class BoletimController {

	@Autowired
	private BoletimService boletimService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority( 'alunoREAD' )") 
	@GetMapping(value="/gera/{alunoId}/{anoLetivoId}")
	public ResponseEntity<Object> geraBoletim(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long alunoId, 
			@PathVariable Long anoLetivoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			BoletimResponse resp = boletimService.geraBoletim( alunoId, anoLetivoId, tokenInfos );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
}
