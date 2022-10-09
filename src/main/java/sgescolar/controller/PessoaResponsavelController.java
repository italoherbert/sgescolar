package sgescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sgescolar.model.request.SavePessoaResponsavelRequest;
import sgescolar.model.response.ErroResponse;
import sgescolar.model.response.ResponsavelBuscadoResponse;
import sgescolar.msg.SistemaException;
import sgescolar.service.PessoaResponsavelService;
import sgescolar.validacao.PessoaResponsavelValidator;
import sgescolar.validacao.PessoaValidator;
import sgescolar.validacao.ValidacaoException;

@RestController
@RequestMapping("/api/responsavel")
public class PessoaResponsavelController {

	@Autowired
	private PessoaResponsavelService pessoaResponsavelService;
	
	@Autowired
	private PessoaResponsavelValidator responsavelValidator;
	
	@Autowired
	private PessoaValidator pessoaValidator;
		
	@PreAuthorize("hasAuthority('alunoREAD')" )	
	@GetMapping(value="/busca/cpf/{cpf}")
	public ResponseEntity<Object> buscaPorCpf( @PathVariable String cpf ) {
		try {
			pessoaValidator.validaCpf( cpf );
			ResponsavelBuscadoResponse resp = pessoaResponsavelService.buscaPorCpf( cpf );
		return ResponseEntity.ok( resp );
		} catch ( SistemaException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@PreAuthorize("hasAuthority('alunoREAD')" )	
	@PostMapping(value="/valida")
	public ResponseEntity<Object> validaDadosResponsavel( @RequestBody SavePessoaResponsavelRequest request ) {
		try {
			responsavelValidator.validaResponsavelRequest( request );
			return ResponseEntity.ok().build();
		} catch ( ValidacaoException e ) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
