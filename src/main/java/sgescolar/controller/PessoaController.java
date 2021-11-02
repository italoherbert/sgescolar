package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.response.PessoaBuscadaResponse;
import sgescolar.service.PessoaService;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
		
	@GetMapping(value="/busca/cpf/{cpf}")
	public ResponseEntity<Object> buscaPorCpf( @PathVariable String cpf ) {
		PessoaBuscadaResponse resp = pessoaService.buscaPorCpf( cpf );
		return ResponseEntity.ok( resp );
	}
	
}
