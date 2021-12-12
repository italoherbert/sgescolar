package sgescolar.builder;

import java.io.IOException;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.response.PlanejamentoAnexoResponse;

@Component
public class PlanejamentoAnexoBuilder {
	
	public void carregaPlanejamentoAnexo( PlanejamentoAnexo anexo, MultipartFile file ) {
		anexo.setArquivoNome( file.getOriginalFilename() ); 
		try {
			anexo.setArquivo( BlobProxy.generateProxy( file.getInputStream(), file.getSize() ) ); 
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public void carregaPlanejamentoAnexoResponse( PlanejamentoAnexoResponse resp, PlanejamentoAnexo anexo ) {
		resp.setId( anexo.getId() );
		resp.setArquivoNome( anexo.getArquivoNome() );
	}
	
	public PlanejamentoAnexo novoPlanejamentoAnexo( Planejamento planejamento ) {
		PlanejamentoAnexo anexo = new PlanejamentoAnexo();
		anexo.setPlanejamento( planejamento );
		return anexo;
	}
	
	public PlanejamentoAnexoResponse novoPlanejamentoAnexoResponse() {
		return new PlanejamentoAnexoResponse();
	}
	
}
