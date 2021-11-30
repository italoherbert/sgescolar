package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Aula;
import sgescolar.model.Disciplina;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.AulaResponse;
import sgescolar.model.response.TurmaDisciplinaResponse;

@Component
public class TurmaDisciplinaBuilder {

	@Autowired
	private DisciplinaBuilder disciplinaBuilder;
		
	@Autowired
	private AulaBuilder aulaBuilder;
	
	public void carregaTurmaDisciplinaResponse( TurmaDisciplinaResponse resp, TurmaDisciplina td ) {
		resp.setId( td.getId() );
		resp.setTurmaId( td.getTurma().getId() );
		
		disciplinaBuilder.carregaDisciplinaResponse( resp.getDisciplina(), td.getDisciplina() );
		
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
		TurmaDisciplinaResponse resp = new TurmaDisciplinaResponse();
		resp.setDisciplina( disciplinaBuilder.novoDisciplinaResponse() );
		return resp;
	}
	
}
