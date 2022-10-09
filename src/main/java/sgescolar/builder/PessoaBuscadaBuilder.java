package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Pessoa;
import sgescolar.model.PessoaResponsavel;
import sgescolar.model.response.ResponsavelBuscadoResponse;

@Component
public class PessoaBuscadaBuilder {
		
	@Autowired
	private PessoaBuilder pessoaBuilder;	
	
	@Autowired
	private PessoaResponsavelBuilder paiOuMaeBuilder;
	
	public ResponsavelBuscadoResponse novoPessoaBuscadaResponse( PessoaResponsavel paiOuMae ) {
		ResponsavelBuscadoResponse resp = new ResponsavelBuscadoResponse();
		resp.setPessoaResponsavelEncontrada( "true" );
		resp.setPessoaEncontrada( "false" );
		resp.setPessoa( null );
		resp.setPessoaResponsavel( paiOuMaeBuilder.novoPessoaResponsavelResponse() );
		
		paiOuMaeBuilder.carregaPessoaResponsavelResponse( resp.getPessoaResponsavel(), paiOuMae );
		return resp;
	}
	
	public ResponsavelBuscadoResponse novoPessoaBuscadaResponse( Pessoa pessoa ) {
		ResponsavelBuscadoResponse resp = new ResponsavelBuscadoResponse();
		resp.setPessoaResponsavelEncontrada( "false" );
		resp.setPessoaEncontrada( "true" );
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() );
		resp.setPessoaResponsavel( null );
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), pessoa );		
		return resp;
	}

	public ResponsavelBuscadoResponse novoPessoaBuscadaNaoEncontradaResponse() {
		ResponsavelBuscadoResponse resp = new ResponsavelBuscadoResponse();
		resp.setPessoaResponsavelEncontrada( "false" );
		resp.setPessoaEncontrada( "false" );
		return resp;
	}
	
}

