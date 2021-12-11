package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.BNCCHabilidade;
import sgescolar.model.request.SaveBNCCHabilidadeRequest;
import sgescolar.model.response.BNCCHabilidadeResponse;

@Component
public class BNCCHabilidadeBuilder {
				
	public void carregaBNCCHabilidade( BNCCHabilidade h, SaveBNCCHabilidadeRequest request ) {		
		h.setCodigo( request.getCodigo() );
		h.setHabilidade( request.getHabilidade() );
	}
	
	public void carregaBNCCHabilidadeResponse( BNCCHabilidadeResponse resp, BNCCHabilidade h ) {
		resp.setCodigo( h.getCodigo() );
		resp.setHabilidade( h.getHabilidade() );				
	}
			
	public BNCCHabilidade novoBNCCHabilidade() {
		return new BNCCHabilidade();		
	}
	
	public BNCCHabilidadeResponse novoBNCCHabilidadeResponse() {
		return new BNCCHabilidadeResponse();		
	}
	
}
