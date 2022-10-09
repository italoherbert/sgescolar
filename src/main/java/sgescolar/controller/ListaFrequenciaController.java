package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.filtro.FiltraListaAlunoFrequenciaRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.GrupoListaAlunoFrequenciaResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.ListaFrequenciaService;
import sgescolar.validacao.GrupoListaAlunoFrequenciaValidator;

@RestController
@RequestMapping(value="/api/lista-frequencia/")
public class ListaFrequenciaController {

	@Autowired
	private ListaFrequenciaService grupoLAFsService;
	
	@Autowired
	private GrupoListaAlunoFrequenciaValidator grupoLAFsValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
		
	@PreAuthorize("hasAuthority('listaFrequenciaWRITE')" )	
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
	
	@PreAuthorize("hasAuthority('listaFrequenciaREAD')" )	
	@PostMapping(value="/filtra/{turmaDisciplinaId}")
	public ResponseEntity<Object> buscaLAF(
			@RequestHeader( "Authorization" ) String auth,
			@PathVariable Long turmaDisciplinaId,
			@RequestBody FiltraListaAlunoFrequenciaRequest request ) {		

		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			grupoLAFsValidator.validaBuscaRequest( request );
			GrupoListaAlunoFrequenciaResponse resp = grupoLAFsService.buscaGrupoLAFs( turmaDisciplinaId, request, tokenInfos );
			return ResponseEntity.ok( resp ); 
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}		
	}
	
}

