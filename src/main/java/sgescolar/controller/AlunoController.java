package sgescolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import sgescolar.model.request.SaveAlunoRequest;
import sgescolar.model.request.filtro.FiltraAlunosRequest;
import sgescolar.model.response.AlunoResponse;
import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.AlunoService;
import sgescolar.validacao.AlunoValidator;

@RestController
@RequestMapping(value="/api/aluno") 
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private AlunoValidator alunoValidator;
			
	@PreAuthorize("hasAuthority('alunoWRITE')")
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registra( @RequestBody SaveAlunoRequest req ) {
		try {
			alunoValidator.validaSaveRequest( req );
			alunoService.registraAluno( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('alunoWRITE')")
	@PutMapping(value="/atualiza/{alunoId}")
	public ResponseEntity<Object> atualiza( @PathVariable Long alunoId, @RequestBody SaveAlunoRequest req ) {		
			try {
			alunoValidator.validaSaveRequest( req );
			alunoService.alteraAluno( alunoId, req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		} 
	}
			
	@PreAuthorize("hasAuthority('alunoREAD')")
	@PostMapping(value="/filtra")
	public ResponseEntity<Object> filtra( @RequestBody FiltraAlunosRequest request ) {	
		try {
			alunoValidator.validaFiltroRequest( request );
			List<AlunoResponse> lista = alunoService.filtraAlunos( request, Pageable.unpaged() );
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('alunoREAD')")
	@PostMapping(value="/filtra/{limit}")
	public ResponseEntity<Object> filtra( 
			@PathVariable Integer limit, 
			@RequestBody FiltraAlunosRequest request ) {
		
		try {
			alunoValidator.validaFiltroRequest( request );
			List<AlunoResponse> lista = alunoService.filtraAlunos( request, PageRequest.of( 0, limit ) ); 
			return ResponseEntity.ok( lista );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('alunoREAD')")
	@GetMapping(value="/get/{alunoId}")
	public ResponseEntity<Object> busca( @PathVariable Long alunoId ) {				
		try {
			AlunoResponse resp = alunoService.buscaAluno( alunoId );
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('alunoDELETE')")
	@DeleteMapping(value="/deleta/{alunoId}")
	public ResponseEntity<Object> deleta( @PathVariable Long alunoId ) {
		try {
			alunoService.deletaAluno( alunoId );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}

