package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.builder.util.TurmaUtil;
import sgescolar.model.Aula;
import sgescolar.model.Curso;
import sgescolar.model.Disciplina;
import sgescolar.model.Serie;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.TurmaDisciplinaResponse;

@Component
public class TurmaDisciplinaBuilder {
			
	@Autowired
	private AulaBuilder aulaBuilder;
	
	@Autowired
	private TurmaUtil turmaUtil;
	
	public void carregaTurmaDisciplinaResponse( TurmaDisciplinaResponse resp, TurmaDisciplina td ) {
		resp.setId( td.getId() );
		
		Disciplina d = td.getDisciplina();
		Turma t = td.getTurma();
		Serie s = t.getSerie();
		Curso c = s.getCurso();		
		
		resp.setDisciplinaId( d.getId() );
		resp.setTurmaId( t.getId() );
		resp.setSerieId( s.getId() );
		resp.setCursoId( c.getId() );

		resp.setDisciplinaSigla( d.getSigla() );
		resp.setDisciplinaDescricao( d.getDescricao() ); 
		resp.setTurmaDescricao( t.getDescricao() );
		resp.setSerieDescricao( s.getDescricao() );
		resp.setCursoDescricao( c.getDescricao() );
				
		resp.setTurmaDescricaoDetalhada( turmaUtil.getDescricaoDetalhada( t ) ); 
		
		List<AulaResponse> responses = new ArrayList<>();

		List<Aula> aulas = td.getAulas();
		for( Aula a : aulas ) {
			AulaResponse aresp = aulaBuilder.novoAulaResponse();
			aulaBuilder.carregaAulaResponse( aresp, a );
			
			responses.add( aresp );
		}
		
		resp.setAulas( responses ); 
	}
	
	public TurmaDisciplina novoTurmaDisciplina( Turma t, Disciplina d ) {
		TurmaDisciplina turmaDisciplina = new TurmaDisciplina();
		turmaDisciplina.setTurma( t );
		turmaDisciplina.setDisciplina( d );
		return turmaDisciplina;
	}
	
	public TurmaDisciplinaResponse novoTurmaDisciplinaResponse() {
		return new TurmaDisciplinaResponse();
	}
	
}
