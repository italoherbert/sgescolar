package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Curso;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class CursoNomeFieldOf implements EntityFieldOf<Curso, String> {

	@Override
	public Expression<String> exp( Root<Curso> root ) {
		return root.get( "nome" ); 
	}

}

