package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.ErroResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.PessoaService;
import sgescolar.validacao.PessoaValidator;

@RestController
@RequestMapping(value="/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaValidator pessoaValidator;
			
	@GetMapping(value="/cpf/disponivel/{cpf}")
	public ResponseEntity<Object> buscaPorCpf( @PathVariable String cpf ) {
		try {
			pessoaValidator.validaCpf( cpf );
			pessoaService.validaSeExisteCpf( cpf );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );			
		}
	}
	
}
