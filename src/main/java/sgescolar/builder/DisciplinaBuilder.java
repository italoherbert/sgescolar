package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Disciplina;
import sgescolar.model.Serie;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.model.response.DisciplinaResponse;

@Component
public class DisciplinaBuilder {
	
	@Autowired
	private SerieBuilder serieBuilder;
				
	public void carregaDisciplina( Disciplina d, SaveDisciplinaRequest request ) {		
		d.setDescricao( request.getDescricao() );
		d.setSigla( request.getSigla() );
	}
	
	public void carregaDisciplinaResponse( DisciplinaResponse resp, Disciplina d ) {
		resp.setId( d.getId() );
		resp.setDescricao( d.getDescricao() );
		resp.setSigla( d.getSigla() ); 
				
		serieBuilder.carregaSerieResponse( resp.getSerie(), d.getSerie() ); 
	}
			
	public Disciplina novoDisciplina( Serie serie ) {
		Disciplina d = new Disciplina();
		d.setSerie( serie );
		return d;
	}
	
	public DisciplinaResponse novoDisciplinaResponse() {
		DisciplinaResponse resp = new DisciplinaResponse();
		resp.setSerie( serieBuilder.novoSerieResponse() ); 
		return resp;
	}

}

