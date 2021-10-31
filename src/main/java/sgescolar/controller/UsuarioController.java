package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.BuscaUsuariosRequest;
import sgescolar.model.request.SaveUsuarioRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.UsuarioResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.UsuarioService;
import sgescolar.validacao.UsuarioValidator;

@RestController
@RequestMapping(value="/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

	@PreAuthorize("hasAuthority('usuarioWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveUsuarioRequest req ) {		
		try {
			usuarioValidator.validaSaveRequest( req );
			usuarioService.registraUsuario( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('usuarioWRITE')")
	@PutMapping(value="/atualiza/{usuarioId}")
	public ResponseEntity<Object> atualiza( @PathVariable Long usuarioId, @RequestBody SaveUsuarioRequest req ) {		
		try {
			usuarioValidator.validaSaveRequest( req );
			usuarioService.alteraUsuario( usuarioId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
	
	@PreAuthorize("hasAuthority('usuarioREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( @RequestBody BuscaUsuariosRequest request ) {
		try {
			usuarioValidator.validaBuscaRequest( request );

			List<UsuarioResponse> lista = usuarioService.filtraUsuarios( request );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 					
	}
	
	@PreAuthorize("hasAuthority('usuarioREAD')")
	@GetMapping(value="/get/{usuarioId}")
	public ResponseEntity<Object> busca( @PathVariable Long usuarioId ) {				
		try {
			UsuarioResponse resp = usuarioService.buscaUsuario( usuarioId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
}
