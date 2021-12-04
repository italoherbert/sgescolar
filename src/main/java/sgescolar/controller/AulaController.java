package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.FiltraAulasRequest;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.AulaService;
import sgescolar.service.ServiceException;

@RestController
@RequestMapping(value="/api/aula")
public class AulaController {
	
	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('aulaREAD')")
	@PostMapping("/filtra/porsemanadia/{turmaDisciplinaId}")
	public ResponseEntity<Object> listaPorTDisESemanaDia(
			@RequestHeader( "Authorization") String auth,
			@PathVariable Long turmaDisciplinaId,
			@RequestBody FiltraAulasRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth ); 
			List<AulaResponse> responses = aulaService.filtraPorSemanaDia( turmaDisciplinaId, request, tokenInfos );
			return ResponseEntity.ok( responses );
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
		
	}

}
