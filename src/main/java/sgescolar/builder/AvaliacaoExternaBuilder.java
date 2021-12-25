package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.AvaliacaoExterna;
import sgescolar.model.Matricula;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAvaliacaoExternaRequest;
import sgescolar.model.response.AvaliacaoExternaResponse;

@Component
public class AvaliacaoExternaBuilder {
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaAvaliacaoExterna( AvaliacaoExterna aext, SaveAvaliacaoExternaRequest request ) {
		aext.setPeso( conversorUtil.stringParaDouble( request.getPeso() ) );
		aext.setMedia( conversorUtil.stringParaDouble( request.getMedia() ) );		
	}
	
	public void carregaAvaliacaoExternaResponse( AvaliacaoExternaResponse resp, AvaliacaoExterna aext ) {
		resp.setId( aext.getId() );
		resp.setMedia( conversorUtil.doubleParaString( aext.getMedia() ) );
		resp.setPeso( conversorUtil.doubleParaString( aext.getPeso() ) );
	}
	
	public AvaliacaoExterna novoAvaliacaoExterna( Matricula matricula, TurmaDisciplina turmaDisciplina ) {
		AvaliacaoExterna avaliacao = new AvaliacaoExterna();
		avaliacao.setMatricula( matricula ); 
		avaliacao.setTurmaDisciplina( turmaDisciplina ); 
		return avaliacao;
	}
	
	public AvaliacaoExternaResponse novoAvaliacaoExternaResponse() {
		return new AvaliacaoExternaResponse();		
	}
	
}

