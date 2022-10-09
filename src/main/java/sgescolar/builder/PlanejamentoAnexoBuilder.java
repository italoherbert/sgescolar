package sgescolar.builder;

import java.io.IOException;

import javax.transaction.Transactional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import sgescolar.model.Arquivo;
import sgescolar.model.Planejamento;
import sgescolar.model.PlanejamentoAnexo;
import sgescolar.model.response.FilePlanejamentoAnexoResponse;
import sgescolar.model.response.PlanejamentoAnexoResponse;

@Component
public class PlanejamentoAnexoBuilder {
	
	@Transactional
	public void carregaPlanejamentoAnexo( PlanejamentoAnexo anexo, MultipartFile file ) {
		anexo.setArquivoNome( file.getOriginalFilename() ); 
		try {
			Arquivo a = new Arquivo();
			a.setArquivo( BlobProxy.generateProxy( file.getInputStream(), file.getSize() ) );
			anexo.setArquivo( a );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	public void carregaPlanejamentoAnexoResponse( PlanejamentoAnexoResponse resp, PlanejamentoAnexo anexo ) {
		resp.setId( anexo.getId() );
		resp.setArquivoNome( anexo.getArquivoNome() );
	}
	
	@Transactional
	public void carregaFilePlanejamentoAnexoResponse( FilePlanejamentoAnexoResponse resp, PlanejamentoAnexo anexo ) {
		resp.setId( anexo.getId() );
		resp.setArquivoNome( anexo.getArquivoNome() );		
		resp.setArquivo( anexo.getArquivo().getArquivo() ); 
	}
	
	public PlanejamentoAnexo novoPlanejamentoAnexo( Planejamento planejamento ) {
		PlanejamentoAnexo anexo = new PlanejamentoAnexo();
		anexo.setPlanejamento( planejamento );
		return anexo;
	}
	
	public PlanejamentoAnexoResponse novoPlanejamentoAnexoResponse() {
		return new PlanejamentoAnexoResponse();
	}
	
	public FilePlanejamentoAnexoResponse novoFilePlanejamentoAnexoResponse() {
		return new FilePlanejamentoAnexoResponse();
	}
	
}
