package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Endereco;
import sgescolar.model.request.SaveEnderecoRequest;
import sgescolar.model.response.EnderecoResponse;

@Component
public class EnderecoBuilder {

	public void carregaEndereco( Endereco e, SaveEnderecoRequest request ) {
		e.setLogradouro( request.getLogradouro() );
		e.setComplemento( request.getComplemento() );
		e.setBairro( request.getBairro() );
		e.setCidade( request.getCidade() );
		e.setUf( request.getUf() );
		e.setCep( request.getCep() );
	}
	
	public void carregaEnderecoResponse( EnderecoResponse resp, Endereco e ) {
		resp.setId( e.getId() );
		resp.setLogradouro( e.getLogradouro() );
		resp.setComplemento( e.getComplemento() );
		resp.setBairro( e.getBairro() );
		resp.setCidade( e.getCidade() );
		resp.setUf( e.getUf() );
		resp.setCep( e.getCep() ); 
	}
	
	public Endereco novoEndereco() {
		return new Endereco();
	}
	
	public EnderecoResponse novoEnderecoResponse() {
		return new EnderecoResponse();
	}
	
}

