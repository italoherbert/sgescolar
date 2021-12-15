package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveAgendamentoAvaliacaoRequest;
import sgescolar.model.request.SaveResultadoAvaliacaoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.security.jwt.JwtTokenUtil;
import sgescolar.security.jwt.TokenInfos;
import sgescolar.service.AvaliacaoService;
import sgescolar.service.filtro.AvaliacoesListagem;
import sgescolar.service.filtro.avaliacao.NaoRealizadasAvaliacoesListagem;
import sgescolar.service.filtro.avaliacao.TodasAvaliacoesListagem;
import sgescolar.validacao.AvaliacaoValidator;

@RestController
@RequestMapping(value="/api/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Autowired
	private AvaliacaoValidator avaliacaoValidator;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PreAuthorize("hasAuthority('avaliacaoWRITE')" )
	@PostMapping(value="/salva/agendamento/{turmaDisciplinaId}")
	public ResponseEntity<Object> agendaAvaliacao( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaDisciplinaId, 
			@RequestBody SaveAgendamentoAvaliacaoRequest request ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			avaliacaoValidator.validaAgendamentoSaveRequest( request );
			avaliacaoService.agendaAvaliacao( turmaDisciplinaId, request, tokenInfos);
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoWRITE')" )
	@PostMapping(value="/salva/resultado/{avaliacaoId}")
	public ResponseEntity<Object> salvaAvaliacao( 
			@RequestHeader("Authorization") String auth,
			@PathVariable Long avaliacaoId, 
			@RequestBody SaveResultadoAvaliacaoRequest request ) {
		
		try {
			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			avaliacaoValidator.validaResultadoSaveRequest( request );
			avaliacaoService.salvaResultadoAvaliacao( avaliacaoId, request, tokenInfos);
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoREAD')" )
	@GetMapping(value="/lista/{turmaDisciplinaId}")
	public ResponseEntity<Object> listaPorTurmaDisciplina(  
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaDisciplinaId ) {
		
		try {
			AvaliacoesListagem listagem = new TodasAvaliacoesListagem();
			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<AvaliacaoResponse> resps = avaliacaoService.listaAvaliacoes( turmaDisciplinaId, tokenInfos, listagem );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoREAD')" )
	@GetMapping(value="/lista/naorealizadas/{turmaDisciplinaId}")
	public ResponseEntity<Object> listaNaoRealizadasPorTurmaDisciplina(  
			@RequestHeader("Authorization") String auth,
			@PathVariable Long turmaDisciplinaId ) {
		
		try {
			AvaliacoesListagem listagem = new NaoRealizadasAvaliacoesListagem();
			
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			List<AvaliacaoResponse> resps = avaliacaoService.listaAvaliacoes( turmaDisciplinaId, tokenInfos, listagem );
			return ResponseEntity.ok( resps );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoREAD')" )
	@GetMapping(value="/get/{avaliacaoId}")
	public ResponseEntity<Object> getAvaliacao(  
			@RequestHeader("Authorization") String auth,
			@PathVariable Long avaliacaoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			AvaliacaoResponse resp = avaliacaoService.getAvaliacao( avaliacaoId, tokenInfos);
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('avaliacaoDELETE')" )
	@DeleteMapping(value="/deleta/{avaliacaoId}")
	public ResponseEntity<Object> deletaAvaliacao(  
			@RequestHeader("Authorization") String auth,
			@PathVariable Long avaliacaoId ) {
		
		try {
			TokenInfos tokenInfos = jwtTokenUtil.getBearerTokenInfos( auth );
			avaliacaoService.removeAvaliacao( avaliacaoId, tokenInfos );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
