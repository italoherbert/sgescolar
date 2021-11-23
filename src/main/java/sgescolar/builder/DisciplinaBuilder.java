package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Disciplina;
import sgescolar.model.Professor;
import sgescolar.model.Turma;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.model.response.DisciplinaResponse;

@Component
public class DisciplinaBuilder {
	
	@Autowired
	private TurmaBuilder turmaBuilder;
			
	public void carregaDisciplina( Disciplina d, SaveDisciplinaRequest request ) {		
		d.setDescricao( request.getDescricao() );
	}
	
	public void carregaDisciplinaResponse( DisciplinaResponse resp, Disciplina d ) {
		resp.setId( d.getId() );
		resp.setDescricao( d.getDescricao() );
		
		String professorNome = d.getProfessor().getFuncionario().getPessoa().getNome();		
		resp.setProfessorNome( professorNome ); 
		
		turmaBuilder.carregaTurmaResponse( resp.getTurma(), d.getTurma() ); 
	}
	
	public Disciplina novoDisciplina( Turma turma, Professor prof ) {
		Disciplina d = new Disciplina();
		d.setTurma( turma );
		d.setProfessor( prof );
		return d;
	}
	
	public DisciplinaResponse novoDisciplinaResponse() {
		DisciplinaResponse resp = new DisciplinaResponse();
		resp.setTurma( turmaBuilder.novoTurmaResponse() ); 
		return resp;
	}

}

