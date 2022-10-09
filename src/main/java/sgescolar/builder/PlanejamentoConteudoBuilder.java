package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoConteudo;
import sgescolar.model.request.SavePlanejamentoConteudoRequest;
import sgescolar.model.response.PlanejamentoConteudoResponse;

@Component
public class PlanejamentoConteudoBuilder {

	public void carregaPlanejamentoConteudo( PlanejamentoConteudo con, SavePlanejamentoConteudoRequest request ) {
		con.setConteudo( request.getConteudo() ); 
	}
	
	public void carregaPlanejamentoConteudoResponse( PlanejamentoConteudoResponse resp, PlanejamentoConteudo con ) {
		resp.setId( con.getId() );
		resp.setConteudo( con.getConteudo() );
	}
	
	public PlanejamentoConteudo novoPlanejamentoConteudo( Planejamento planejamento ) {
		PlanejamentoConteudo con = new PlanejamentoConteudo();
		con.setPlanejamento( planejamento );
		return con;
	}
	
	public PlanejamentoConteudoResponse novoPlanejamentoConteudoResponse() {
		return new PlanejamentoConteudoResponse();
	}
	
}
