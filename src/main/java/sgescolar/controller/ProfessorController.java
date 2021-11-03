package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.enums.tipos.UsuarioPerfil;
import sgescolar.model.request.FiltraProfessoresRequest;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.ProfessorResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.ProfessorService;
import sgescolar.validacao.ProfessorValidator;

@RestController
@RequestMapping(value="/api/professor") 
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private ProfessorValidator professorValidator;
			
	@PreAuthorize("hasAuthority('professorWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveProfessorRequest req ) {		
		req.getFuncionario().getUsuario().setPerfil( UsuarioPerfil.PROFESSOR.name() );		
		try {
			professorValidator.validaSaveRequest( req );
			professorService.registraProfessor( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('professorWRITE')")
	@PutMapping(value="/atualiza/{professorId}")
	public ResponseEntity<Object> atualiza( @PathVariable Long professorId, @RequestBody SaveProfessorRequest req ) {		
		req.getFuncionario().getUsuario().setPerfil( UsuarioPerfil.PROFESSOR.name() );		
		try {
			professorValidator.validaSaveRequest( req );
			professorService.alteraProfessor( professorId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('professorREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( @RequestBody FiltraProfessoresRequest request ) {
		try {
			professorValidator.validaFiltroRequest( request );
			List<ProfessorResponse> lista = professorService.filtraProfessores( request );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('professorREAD')")
	@GetMapping(value="/get/{professorId}")
	public ResponseEntity<Object> busca( @PathVariable Long professorId ) {				
		try {
			ProfessorResponse resp = professorService.buscaProfessor( professorId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('professorDELETE')")
	@DeleteMapping(value="/deleta/{professorId}")
	public ResponseEntity<Object> deleta( @PathVariable Long professorId ) {
		try {
			professorService.deletaProfessor( professorId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}