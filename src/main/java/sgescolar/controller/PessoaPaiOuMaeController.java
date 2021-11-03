package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SavePessoaPaiOuMaeRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.PaiOuMaeBuscadoResponse;
import sgescolar.service.PessoaPaiOuMaeService;
import sgescolar.validacao.PessoaPaiOuMaeValidator;
import sgescolar.validacao.ValidacaoException;

@RestController
@RequestMapping("/api/paioumae")
public class PessoaPaiOuMaeController {

	@Autowired
	private PessoaPaiOuMaeService pessoaPaiOuMaeService;
	
	@Autowired
	private PessoaPaiOuMaeValidator paiOuMaeValidator;
		
	@GetMapping(value="/busca/cpf/{cpf}")
	public ResponseEntity<Object> buscaPorCpf( @PathVariable String cpf ) {
		PaiOuMaeBuscadoResponse resp = pessoaPaiOuMaeService.buscaPorCpf( cpf );
		return ResponseEntity.ok( resp );
	}
	
	@PostMapping(value="/valida")
	public ResponseEntity<Object> validaDadosPaiOuMae( @RequestBody SavePessoaPaiOuMaeRequest request ) {
		try {
			paiOuMaeValidator.validaPaiOuMaeRequest( request );
			return ResponseEntity.ok().build();
		} catch ( ValidacaoException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
