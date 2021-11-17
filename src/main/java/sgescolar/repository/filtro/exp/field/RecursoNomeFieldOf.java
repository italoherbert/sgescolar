package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Recurso;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class RecursoNomeFieldOf implements EntityFieldOf<Recurso, String> {

	@Override
	public Expression<String> exp( Root<Recurso> root ) {
		return root.get( "nome" );  
	}

}
