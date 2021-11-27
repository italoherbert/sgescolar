package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Disciplina;
import sgescolar.model.Turma;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.response.TurmaDisciplinaResponse;

@Component
public class TurmaDisciplinaBuilder {

	@Autowired
	private DisciplinaBuilder disciplinaBuilder;
	
	@Autowired
	private TurmaBuilder turmaBuilder;
	
	public void carregaTurmaDisciplinaResponse( TurmaDisciplinaResponse resp, TurmaDisciplina td ) {
		resp.setId( td.getId());
		turmaBuilder.carregaTurmaResponse( resp.getTurma(), td.getTurma() ); 
		disciplinaBuilder.carregaDisciplinaResponse( resp.getDisciplina(), td.getDisciplina() ); 
	}
	
	public TurmaDisciplina novoTurmaDisciplina( Turma t, Disciplina d ) {
		TurmaDisciplina turmaDisciplina = new TurmaDisciplina();
		turmaDisciplina.setTurma( t );
		turmaDisciplina.setDisciplina( d );
		return turmaDisciplina;
	}
	
	public TurmaDisciplinaResponse novoTurmaDisciplinaResponse() {
		TurmaDisciplinaResponse resp = new TurmaDisciplinaResponse();
		resp.setTurma( turmaBuilder.novoTurmaResponse() );
		resp.setDisciplina( disciplinaBuilder.novoDisciplinaResponse() ); 
		return resp;
	}
	
}
