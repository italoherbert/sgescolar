package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Pessoa;
import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.response.PaiOuMaeBuscadoResponse;

@Component
public class PessoaBuscadaBuilder {
		
	@Autowired
	private PessoaBuilder pessoaBuilder;	
	
	@Autowired
	private PessoaPaiOuMaeBuilder paiOuMaeBuilder;
	
	public PaiOuMaeBuscadoResponse novoPessoaBuscadaResponse( PessoaPaiOuMae paiOuMae ) {
		PaiOuMaeBuscadoResponse resp = new PaiOuMaeBuscadoResponse();
		resp.setPessoaPaiOuMaeEncontrado( "true" );
		resp.setPessoaEncontrada( "false" );
		resp.setPessoa( null );
		resp.setPessoaPaiOuMae( paiOuMaeBuilder.novoPessoaPaiOuMaeResponse() );
		
		paiOuMaeBuilder.carregaPessoaPaiOuMaeResponse( resp.getPessoaPaiOuMae(), paiOuMae );
		return resp;
	}
	
	public PaiOuMaeBuscadoResponse novoPessoaBuscadaResponse( Pessoa pessoa ) {
		PaiOuMaeBuscadoResponse resp = new PaiOuMaeBuscadoResponse();
		resp.setPessoaPaiOuMaeEncontrado( "false" );
		resp.setPessoaEncontrada( "true" );
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setPessoaPaiOuMae( null );
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), pessoa );		
		return resp;
	}

	public PaiOuMaeBuscadoResponse novoPessoaBuscadaNaoEncontradaResponse() {
		PaiOuMaeBuscadoResponse resp = new PaiOuMaeBuscadoResponse();
		resp.setPessoaPaiOuMaeEncontrado( "false" );
		resp.setPessoaEncontrada( "false" );
		return resp;
	}
	
}

