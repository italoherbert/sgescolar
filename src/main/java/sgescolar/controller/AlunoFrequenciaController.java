package sgescolar.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.BuscaGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.GrupoListaAlunoFrequenciaService;
import sgescolar.util.DataUtil;
import sgescolar.validacao.GrupoListaAlunoFrequenciaValidator;

@RestController
@RequestMapping(value="/api/lista-frequencia/")
public class AlunoFrequenciaController {

	@Autowired
	private GrupoListaAlunoFrequenciaService grupoLAFsService;
	
	@Autowired
	private GrupoListaAlunoFrequenciaValidator grupoLAFsValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private DataUtil dataUtil;
	
	@PostMapping(value="/salva")
	public ResponseEntity<Object> salvaHorario(
			@RequestHeader( "Authorization" ) String auth,
			@RequestBody SaveGrupoListaAlunoFrequenciaRequest request ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			grupoLAFsValidator.validaSaveRequest( request );
			grupoLAFsService.salvaLAFs( request, tokenInfos );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PostMapping(value="/busca")
	public ResponseEntity<Object> buscaLAF(
			@RequestHeader( "Authorization" ) String auth,
			@RequestBody BuscaGrupoListaAlunoFrequenciaRequest request ) {		
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			grupoLAFsValidator.validaBuscaRequest( request );
			GrupoListaAlunoFrequenciaResponse resp = grupoLAFsService.buscaGrupoLAFs( request, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
		
	@PostMapping(value="/busca/hoje")
	public ResponseEntity<Object> buscaHojeGrupoLAFs(
			@RequestHeader( "Authorization" ) String auth ) {		
		
		try {
			Date hoje = dataUtil.dataHoje();
			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			GrupoListaAlunoFrequenciaResponse resp = grupoLAFsService.buscaGrupoLAFs( hoje, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	@PostMapping(value="/busca/ontem")
	public ResponseEntity<Object> buscaOntemGrupoLAFs(
			@RequestHeader( "Authorization" ) String auth ) {		
		
		try {
			Date ontem = dataUtil.dataOntem();
			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			GrupoListaAlunoFrequenciaResponse resp = grupoLAFsService.buscaGrupoLAFs( ontem, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
	
	
}

