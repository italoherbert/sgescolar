package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
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
	
	@PreAuthorize("hasAuthority('horarioWRITE')" )	
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
	
	@PreAuthorize("hasAuthority('horarioREAD')" )	
	@GetMapping(value="/lista/aulas/{turmaDisciplinaId}") 
	public ResponseEntity<Object> listaPorTurmaDisciplina( 
			@RequestHeader("Authorization") String auth,			
			@PathVariable Long turmaDisciplinaId ) {
				
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<AulaResponse> lista = horarioService.listaAulas( turmaDisciplinaId, tokenInfos );
			return ResponseEntity.ok( lista );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
}
