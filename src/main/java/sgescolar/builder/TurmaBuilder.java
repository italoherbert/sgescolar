package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AnoLetivo;
import sgescolar.model.Serie;
import sgescolar.model.Turma;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.model.response.TurmaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class TurmaBuilder {
	
	@Autowired
	private SerieBuilder serieBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaTurma( Turma t, SaveTurmaRequest request ) {		
		t.setDescricao( request.getDescricao() );
	}
	
	public void carregaTurmaResponse( TurmaResponse resp, Turma t ) {
		resp.setId( t.getId() );
		resp.setDescricao( t.getDescricao() );
		
		AnoLetivo al = t.getAnoLetivo();
		resp.setAnoLetivoId( al.getId() ); 
		resp.setAnoLetivoAno( conversorUtil.inteiroParaString( al.getAno() ) ); 
		
		serieBuilder.carregaSerieResponse( resp.getSerie(), t.getSerie() ); 
	}
	
	public Turma novoTurma( Serie serie, AnoLetivo anoLetivo ) {
		Turma t = new Turma();
		t.setSerie( serie );
		t.setAnoLetivo( anoLetivo );
		return t;
	}
	
	public TurmaResponse novoTurmaResponse() {
		TurmaResponse resp = new TurmaResponse();
		resp.setSerie( serieBuilder.novoSerieResponse() );
		return resp;
	}

}

