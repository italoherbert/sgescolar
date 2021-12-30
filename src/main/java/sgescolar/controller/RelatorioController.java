package sgescolar.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.RelatorioService;

@RestController
@RequestMapping(value="/api/relatorio")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping(value="/planejamento/{planejamentoId}/{token}", produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<Object> geraRelatorioPlanoAulas(
			@PathVariable Long planejamentoId,
			@PathVariable String token,
			HttpServletResponse response ) {
		
		try {		
			jwtTokenUtil.validaToken( token );
			
			TokenInfos tokenInfos = jwtTokenUtil.getTokenInfos( token );			
			relatorioService.geraEnviaPlanejamentoRelatorio( planejamentoId, tokenInfos, response );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
