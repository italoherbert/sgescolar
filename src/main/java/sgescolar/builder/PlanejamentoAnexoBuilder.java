package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.request.SavePlanejamentoAnexoRequest;
import sgescolar.model.response.PlanejamentoAnexoResponse;

@Component
public class PlanejamentoAnexoBuilder {

	@Autowired
	private ArquivoBuilder arquivoBuilder;
	
	public void carregaPlanejamentoAnexo( PlanejamentoAnexo anexo, SavePlanejamentoAnexoRequest request ) {
		arquivoBuilder.carregaArquivo( anexo.getArquivo(), request.getArquivo() ); 		
	}
	
	public void carregaPlanejamentoAnexoResponse( PlanejamentoAnexoResponse resp, PlanejamentoAnexo anexo ) {
		resp.setId( anexo.getId() );
		arquivoBuilder.carregaArquivoResponse( resp.getArquivo(), anexo.getArquivo() ); 
	}
	
	public PlanejamentoAnexo novoPlanejamentoAnexo( Planejamento planejamento ) {
		PlanejamentoAnexo anexo = new PlanejamentoAnexo();
		anexo.setPlanejamento( planejamento );
		anexo.setArquivo( arquivoBuilder.novoArquivo() ); 
		return anexo;
	}
	
	public PlanejamentoAnexoResponse novoPlanejamentoAnexoResponse() {
		PlanejamentoAnexoResponse resp = new PlanejamentoAnexoResponse();
		resp.setArquivo( arquivoBuilder.novoArquivoResponse() ); 
		return resp;
	}
	
}
