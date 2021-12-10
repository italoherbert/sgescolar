package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoObjetivo;
import sgescolar.model.request.SavePlanejamentoObjetivoRequest;
import sgescolar.model.response.PlanejamentoObjetivoResponse;

@Component
public class PlanejamentoObjetivoBuilder {

	public void carregaPlanejamentoObjetivo( PlanejamentoObjetivo obj, SavePlanejamentoObjetivoRequest request ) {
		obj.setObjetivo( request.getObjetivo() ); 
	}
	
	public void carregaPlanejamentoObjetivoResponse( PlanejamentoObjetivoResponse resp, PlanejamentoObjetivo obj ) {
		resp.setId( obj.getId() );
		resp.setObjetivo( obj.getObjetivo() );
	}
	
	public PlanejamentoObjetivo novoPlanejamentoObjetivo( Planejamento planejamento ) {
		PlanejamentoObjetivo obj = new PlanejamentoObjetivo();
		obj.setPlanejamento( planejamento );
		return obj;
	}
	
	public PlanejamentoObjetivoResponse novoPlanejamentoObjetivoResponse() {
		return new PlanejamentoObjetivoResponse();
	}
	
}
