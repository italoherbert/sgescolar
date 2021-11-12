package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import sgescolar.model.request.SaveUsuarioGrupoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.UsuarioGrupoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.UsuarioGrupoService;
import sgescolar.validacao.UsuarioGrupoValidator;

@RestController
@RequestMapping(value="/api/usuario/grupo") 
public class UsuarioGrupoController {

	@Autowired
	private UsuarioGrupoService usuarioGrupoService;
	
	@Autowired
	private UsuarioGrupoValidator usuarioGrupoValidator;
				
	@PreAuthorize("hasAuthority('usuarioGrupoWRITE')")
	@PostMapping(value="/recursos/sincroniza/{grupoId}")
	public ResponseEntity<Object> sincronizaRecursos( @PathVariable Long grupoId ) {
		try {			
			usuarioGrupoService.sincronizaRecursos( grupoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}

	@PreAuthorize("hasAuthority('usuarioGrupoWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraGrupo( @RequestBody SaveUsuarioGrupoRequest request ) {
		try {
			usuarioGrupoValidator.validaSaveRequest( request );
			usuarioGrupoService.registraGrupo( request );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('usuarioGrupoWRITE')")
	@PutMapping(value="/atualiza/{grupoId}")
	public ResponseEntity<Object> registraGrupo( @PathVariable Long grupoId, @RequestBody SaveUsuarioGrupoRequest request ) {
		try {
			usuarioGrupoValidator.validaSaveRequest( request );
			usuarioGrupoService.alteraGrupo( grupoId, request );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
			
	@GetMapping(value="/lista")
	public ResponseEntity<Object> buscaGrupos() {
		String[] lista = usuarioGrupoService.listaGrupos();
		return ResponseEntity.ok( lista );
	}
		
	@PreAuthorize("hasAuthority('usuarioGrupoREAD')")
	@GetMapping(value="/get/{grupoId}")
	public ResponseEntity<Object> busca( @PathVariable Long grupoId ) {				
		try {
			UsuarioGrupoResponse resp = usuarioGrupoService.buscaGrupo( grupoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('usuarioGrupoDELETE')")
	@GetMapping(value="/deleta/{grupoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long grupoId ) {				
		try {
			usuarioGrupoService.deletaGrupo( grupoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
