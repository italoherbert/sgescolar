package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Curso;
import sgescolar.model.Serie;
import sgescolar.model.request.SaveSerieRequest;
import sgescolar.model.response.SerieResponse;

@Component
public class SerieBuilder {
			
	@Autowired
	private CursoBuilder cursoBuilder;
	
	public void carregaSerie( Serie s, SaveSerieRequest request ) {		
		s.setDescricao( request.getDescricao() );
	}
	
	public void carregaSerieResponse( SerieResponse resp, Serie s ) {
		resp.setId( s.getId() );
		resp.setDescricao( s.getDescricao() );
				
		cursoBuilder.carregaCursoResponse( resp.getCurso(), s.getCurso() );
	}
	
	public Serie novoSerie( Curso curso ) {
		Serie s = new Serie();
		s.setCurso( curso );
		return s;
	}
	
	public SerieResponse novoSerieResponse() {
		SerieResponse resp = new SerieResponse();
		resp.setCurso( cursoBuilder.novoCursoResponse() );
		return resp;
	}
	
}
