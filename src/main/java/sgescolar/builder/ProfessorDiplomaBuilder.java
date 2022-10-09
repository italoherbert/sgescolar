package sgescolar.builder;

import org.springframework.stereotype.Component;

import sgescolar.model.Professor;
import sgescolar.model.ProfessorDiploma;
import sgescolar.model.request.SaveProfessorDiplomaRequest;
import sgescolar.model.response.ProfessorDiplomaResponse;

@Component
public class ProfessorDiplomaBuilder {
		
	public void carregaProfessorDiploma( ProfessorDiploma prd, SaveProfessorDiplomaRequest request ) {
		prd.setCurso( request.getCurso() );
		prd.setTitulacao( request.getTitulacao() );
	}
	
	public void carregaProfessorDiplomaResponse( ProfessorDiplomaResponse resp, ProfessorDiploma prd ) {
		resp.setId( prd.getId() );
		resp.setCurso( prd.getCurso() );
		resp.setTitulacao( prd.getTitulacao() ); 
	}
	
	public ProfessorDiploma novoProfessorDiploma( Professor professor ) {
		ProfessorDiploma nota = new ProfessorDiploma();
		nota.setProfessor( professor );
		return nota;
	}
		
	public ProfessorDiplomaResponse novoProfessorDiplomaResponse() {
		return new ProfessorDiplomaResponse();		
	}
		
}

