package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Curso;
import sgescolar.model.Escola;
import sgescolar.model.Serie;
import sgescolar.model.request.SaveSerieRequest;
import sgescolar.model.response.SerieResponse;
import sgescolar.util.ConversorUtil;

@Component
public class SerieBuilder {
		
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaSerie( Serie s, SaveSerieRequest request ) {		
		s.setDescricao( request.getDescricao() );
		s.setGrau( conversorUtil.stringParaInteiro( request.getGrau() ) );
	}
	
	public void carregaSerieResponse( SerieResponse resp, Serie s ) {
		resp.setId( s.getId() );
		resp.setDescricao( s.getDescricao() );
		resp.setGrau( conversorUtil.inteiroParaString( s.getGrau() ) );
		
		Curso c = s.getCurso();
		Escola e = c.getEscola();

		resp.setEscolaId( e.getId() );
		resp.setCursoId( c.getId() ); 
		
		resp.setEscolaNome( e.getNome() );	
		resp.setCursoNome( c.getNome() ); 
	}
	
	public Serie novoSerie( Curso curso ) {
		Serie s = new Serie();
		s.setCurso( curso );
		return s;
	}
	
	public SerieResponse novoSerieResponse() {
		return new SerieResponse();
	}
	
}
