package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Professor;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class ProfessorNomeFieldOf implements EntityFieldOf<Professor, String> {

	@Override
	public Expression<String> exp( Root<Professor> root ) {
		return root.join( "funcionario" ).join( "pessoa" ).get( "nome" );  
	}

}