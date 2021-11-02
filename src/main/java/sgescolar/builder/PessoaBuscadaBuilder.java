package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Pessoa;
import sgescolar.model.PessoaPaiOuMae;
import sgescolar.model.response.PessoaBuscadaResponse;

@Component
public class PessoaBuscadaBuilder {
		
	@Autowired
	private PessoaBuilder pessoaBuilder;	
	
	@Autowired
	private PessoaPaiOuMaeBuilder paiOuMaeBuilder;
	
	public PessoaBuscadaResponse novoPessoaBuscadaResponse( PessoaPaiOuMae paiOuMae ) {
		PessoaBuscadaResponse resp = new PessoaBuscadaResponse();
		resp.setEncontradoPaiOuMae( "true" );
		resp.setEncontradaPessoa( "false" );
		resp.setPessoa( null );
		resp.setPaiOuMae( paiOuMaeBuilder.novoPessoaPaiOuMaeResponse() );
		
		paiOuMaeBuilder.carregaPessoaPaiOuMaeResponse( resp.getPaiOuMae(), paiOuMae );
		return resp;
	}
	
	public PessoaBuscadaResponse novoPessoaBuscadaResponse( Pessoa pessoa ) {
		PessoaBuscadaResponse resp = new PessoaBuscadaResponse();
		resp.setEncontradoPaiOuMae( "false" );
		resp.setEncontradaPessoa( "true" );
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setPaiOuMae( null );
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), pessoa );
		return resp;
	}

	public PessoaBuscadaResponse novoPessoaBuscadaNaoEncontradaResponse() {
		PessoaBuscadaResponse resp = new PessoaBuscadaResponse();
		resp.setEncontradoPaiOuMae( "false" );
		resp.setEncontradaPessoa( "false" );
		return resp;
	}
	
}

