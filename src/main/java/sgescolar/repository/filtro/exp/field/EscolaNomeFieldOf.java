package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Escola;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class EscolaNomeFieldOf implements EntityFieldOf<Escola, String> {

	@Override
	public Expression<String> exp( Root<Escola> root ) {
		return root.get( "nome" ); 
	}

}