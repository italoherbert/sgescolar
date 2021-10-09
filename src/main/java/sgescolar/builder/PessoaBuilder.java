package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Pessoa;
import sgescolar.model.request.PessoaRequest;
import sgescolar.model.response.PessoaResponse;

@Component
public class PessoaBuilder {

	public void carregaPessoa( Pessoa p, PessoaRequest request ) {
		p.setNome( request.getNome() );
		p.setEmail( request.getEmail() );
		p.setTelefone( request.getTelefone() );
	}
	
	public void carregaPessoaResponse( PessoaResponse resp, Pessoa p ) {
		resp.setId( p.getId() );
		resp.setNome( p.getNome() );
		resp.setTelefone( p.getTelefone() );
		resp.setEmail( p.getEmail() );
	}
	
	public Pessoa novoPessoa() {
		return new Pessoa();
	}
	
	public PessoaResponse novoPessoaResponse() {
		return new PessoaResponse();
	}
	
}
