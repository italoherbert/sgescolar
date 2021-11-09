package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.PermissaoGrupoService;
import sgescolar.validacao.PermissaoGrupoValidator;

@RestController
@RequestMapping(value="/api/permissao")
public class PermissaoGrupoController {

	@Autowired
	private PermissaoGrupoService permissaoGrupoService;
	
	@Autowired
	private PermissaoGrupoValidator permissaoGrupoValidator;
	
	@PreAuthorize("hasAuthority('permissaoGrupoWRITE')")
	@PostMapping(value="/grupo/salva/{id}")
	public ResponseEntity<Object> salvaPermissaoGrupo( @PathVariable Long id, @RequestBody SavePermissaoGrupoRequest request ) {
		try {
			permissaoGrupoValidator.validaSaveRequestPermissaoGrupo( request );
			permissaoGrupoService.salvaPermissaoGrupo( id, request );
			return 	ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('permissaoGrupoWRITE')")
	@PatchMapping(value="/salva/{id}")
	public ResponseEntity<Object> salvaPermissao( @PathVariable Long id, @RequestBody SavePermissaoRequest request ) {
		try {
			permissaoGrupoValidator.validaSaveRequestPermissao( request );
			permissaoGrupoService.salvaPermissao( id, request );
			return 	ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
