package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Escola;
import sgescolar.model.request.SaveEscolaRequest;
import sgescolar.model.response.EscolaResponse;

@Component
public class EscolaBuilder {

	@Autowired
	private EnderecoBuilder enderecoBuilder;
	
	@Autowired
	private ContatoInfoBuilder contatoInfoBuilder;
	
	public void carregaEscola( Escola e, SaveEscolaRequest request ) {
		e.setNome( request.getNome() );
		
		enderecoBuilder.carregaEndereco( e.getEndereco(), request.getEndereco() );
		contatoInfoBuilder.carregaContatoInfo( e.getContatoInfo(), request.getContatoInfo() );
	}
	
	public void carregaEscolaResponse( EscolaResponse resp, Escola e ) {
		resp.setId( e.getId() );
		resp.setNome( e.getNome() );
		
		enderecoBuilder.carregaEnderecoResponse( resp.getEndereco(), e.getEndereco() );
		contatoInfoBuilder.carregaContatoInfoResponse( resp.getContatoInfo(), e.getContatoInfo() );
	}
	
	public Escola novoEscola() {
		Escola e = new Escola();
		e.setEndereco( enderecoBuilder.novoEndereco() );
		e.setContatoInfo( contatoInfoBuilder.novoContatoInfo() );
		return e;
	}
	
	public EscolaResponse novoEscolaResponse() {
		EscolaResponse resp = new EscolaResponse();
		resp.setEndereco( enderecoBuilder.novoEnderecoResponse() );
		resp.setContatoInfo( contatoInfoBuilder.novoContatoInfoResponse() );
		return resp;
	}
	
}

