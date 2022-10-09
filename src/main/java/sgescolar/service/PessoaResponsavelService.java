package sgescolar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgescolar.builder.PessoaBuscadaBuilder;
import sgescolar.model.Pessoa;
import sgescolar.model.PessoaResponsavel;
import sgescolar.model.response.ResponsavelBuscadoResponse;
import sgescolar.repository.PessoaResponsavelRepository;
import sgescolar.repository.PessoaRepository;

@Service
public class PessoaResponsavelService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaResponsavelRepository paiOuMaeRepository;
	
	@Autowired
	private PessoaBuscadaBuilder pessoaBuscadaBuilder;
		
	public ResponsavelBuscadoResponse buscaPorCpf( String cpf ) {
		Optional<PessoaResponsavel> paiOuMaeOp = paiOuMaeRepository.buscaPorCpf( cpf );
		if ( paiOuMaeOp.isPresent() ) {
			PessoaResponsavel p = paiOuMaeOp.get();
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
