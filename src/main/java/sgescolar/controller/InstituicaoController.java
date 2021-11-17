package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SaveInstituicaoRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.InstituicaoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.InstituicaoService;
import sgescolar.validacao.InstituicaoValidator;

@RestController
@RequestMapping(value="/api/instituicao") 
public class InstituicaoController {

	@Autowired
	private InstituicaoService alunoService;
	
	@Autowired
	private InstituicaoValidator alunoValidator;
			
	@PreAuthorize("hasAuthority('instituicaoWRITE')")
	@PostMapping(value="/salva")
	public ResponseEntity<Object> salva( @RequestBody SaveInstituicaoRequest req ) {
		try {
			alunoValidator.validaSaveRequest( req );
			alunoService.salvaInstituicao( req );
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
	@PreAuthorize("hasAuthority('instituicaoREAD')")
	@GetMapping(value="/get")
	public ResponseEntity<Object> busca() {				
		try {
			InstituicaoResponse resp = alunoService.buscaInstituicao();
			return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('instituicaoDELETE')")
	@DeleteMapping(value="/deleta")
	public ResponseEntity<Object> deleta() {
		try {
			alunoService.deletaInstituicao();
			return ResponseEntity.ok().build();
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	
}


