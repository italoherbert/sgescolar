package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PessoaBuscadaBuilder;
import sgescolar.model.Pessoa;
import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.response.PaiOuMaeBuscadoResponse;
import sgescolar.repository.PessoaPaiOuMaeRepository;
import sgescolar.repository.PessoaRepository;

@Service
public class PessoaPaiOuMaeService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaPaiOuMaeRepository paiOuMaeRepository;
	
	@Autowired
	private PessoaBuscadaBuilder pessoaBuscadaBuilder;
		
	public PaiOuMaeBuscadoResponse buscaPorCpf( String cpf ) {
		Optional<PessoaPaiOuMae> paiOuMaeOp = paiOuMaeRepository.buscaPorCpf( cpf );
		if ( paiOuMaeOp.isPresent() ) {
			PessoaPaiOuMae p = paiOuMaeOp.get();
			return pessoaBuscadaBuilder.novoPessoaBuscadaResponse( p );
		}
		
		Optional<Pessoa> pessoaOp = pessoaRepository.buscaPorCpf( cpf );
		if ( pessoaOp.isPresent() ) {
			Pessoa p = pessoaOp.get();
			return pessoaBuscadaBuilder.novoPessoaBuscadaResponse( p );
		}
		
		return pessoaBuscadaBuilder.novoPessoaBuscadaNaoEncontradaResponse();
	}
	
}
