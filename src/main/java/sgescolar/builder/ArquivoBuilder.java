package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Arquivo;
import sgescolar.model.request.SaveArquivoRequest;
import sgescolar.model.response.ArquivoResponse;

@Component
public class ArquivoBuilder {

	public void carregaArquivo( Arquivo arq, SaveArquivoRequest request ) {
		
	}
	
	public void carregaArquivoResponse( ArquivoResponse resp, Arquivo arq ) {
		resp.setId( arq.getId() );
	}
	
	public Arquivo novoArquivo() {
		return new Arquivo();		
	}
	
	public ArquivoResponse novoArquivoResponse() {
		return new ArquivoResponse();
	}
	
}
