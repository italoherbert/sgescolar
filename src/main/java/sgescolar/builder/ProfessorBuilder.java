package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Professor;
import sgescolar.model.request.SaveProfessorRequest;
import sgescolar.model.response.ProfessorResponse;

@Component
public class ProfessorBuilder {

	@Autowired
	private FuncionarioBuilder funcionarioBuilder;
			
	public void carregaProfessor( Professor p, SaveProfessorRequest request ) {		
		funcionarioBuilder.carregaFuncionario( p.getFuncionario(), request.getFuncionario() );		
	}
	
	public void carregaProfessorResponse( ProfessorResponse resp, Professor p ) {
		resp.setId( p.getId() );
		
		funcionarioBuilder.carregaFuncionarioResponse( resp.getFuncionario(), p.getFuncionario() ); 
	}
	
	public Professor novoProfessor() {
		Professor p = new Professor();
		p.setFuncionario( funcionarioBuilder.novoFuncionario() );
		return p;
	}
	
	public ProfessorResponse novoProfessorResponse() {
		ProfessorResponse resp = new ProfessorResponse();
		resp.setFuncionario( funcionarioBuilder.novoFuncionarioResponse() );
		return resp;
	}
	
}
