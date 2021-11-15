package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.EnderecoLocal;
import sgescolar.model.request.SaveEnderecoLocalRequest;
import sgescolar.model.response.EnderecoLocalResponse;

@Component
public class EnderecoLocalBuilder {

	public void carregaEnderecoLocal( EnderecoLocal e, SaveEnderecoLocalRequest request ) {
		e.setLogradouro( request.getLogradouro() );
		e.setComplemento( request.getComplemento() );
		e.setBairro( request.getBairro() );
		e.setCep( request.getCep() );
	}
	
	public void carregaEnderecoLocalResponse( EnderecoLocalResponse resp, EnderecoLocal e ) {
		resp.setId( e.getId() );
		resp.setLogradouro( e.getLogradouro() );
		resp.setComplemento( e.getComplemento() );
		resp.setBairro( e.getBairro() );
		resp.setCep( e.getCep() ); 
	}
	
	public EnderecoLocal novoEnderecoLocal() {
		return new EnderecoLocal();
	}
	
	public EnderecoLocalResponse novoEnderecoLocalResponse() {
		return new EnderecoLocalResponse();
	}
	
}

