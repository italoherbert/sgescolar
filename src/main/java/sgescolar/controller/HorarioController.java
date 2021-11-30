package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.HorarioService;
import sgescolar.service.ServiceException;

@RestController
@RequestMapping(value="/api/horario")
public class HorarioController {

	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value="/salva/{turmaId}")
	public ResponseEntity<Object> salvaHorario(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaId, 
			@RequestBody SaveHorarioRequest request ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth ); 
			horarioService.salvaHorario( turmaId, request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
}
