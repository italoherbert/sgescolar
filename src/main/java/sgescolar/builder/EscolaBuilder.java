package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Escola;
import sgescolar.model.request.EscolaRequest;
import sgescolar.model.response.EscolaResponse;

@Component
public class EscolaBuilder {

	public void carregaEscola( Escola p, EscolaRequest request ) {
		p.setNome( request.getNome() );
	}
	
	public void carregaEscolaResponse( EscolaResponse resp, Escola p ) {
		resp.setId( p.getId() );
		resp.setNome( p.getNome() );
	}
	
	public Escola novoEscola() {
		return new Escola();
	}
	
	public EscolaResponse novoEscolaResponse() {
		return new EscolaResponse();
	}
	
}

