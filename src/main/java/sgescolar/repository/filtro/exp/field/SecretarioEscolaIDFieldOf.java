package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Secretario;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class SecretarioEscolaIDFieldOf implements EntityFieldOf<Secretario, Long> {

	@Override
	public Expression<Long> exp(Root<Secretario> root) {
		return root.join( "escola" ).get( "id" );
	}

}
