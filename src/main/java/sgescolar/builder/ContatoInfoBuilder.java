package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.ContatoInfo;
import sgescolar.model.request.SaveContatoInfoRequest;
import sgescolar.model.response.ContatoInfoResponse;

@Component
public class ContatoInfoBuilder {

	public void carregaContatoInfo( ContatoInfo ci, SaveContatoInfoRequest request ) {
		ci.setTelefoneFixo( request.getTelefoneFixo() );
		ci.setTelefoneCelular( request.getTelefoneCelular() );
		ci.setEmail( request.getEmail() );
	}
	
	public void carregaContatoInfoResponse( ContatoInfoResponse resp, ContatoInfo ci ) {
		resp.setId( ci.getId() );
		resp.setTelefoneFixo( ci.getTelefoneFixo() );
		resp.setTelefoneCelular( ci.getTelefoneCelular() );
		resp.setEmail( ci.getEmail() );
	}
	
	public ContatoInfo novoContatoInfo() {
		return new ContatoInfo();
	}
	
	public ContatoInfoResponse novoContatoInfoResponse() {
		return new ContatoInfoResponse();
	}
	
}
