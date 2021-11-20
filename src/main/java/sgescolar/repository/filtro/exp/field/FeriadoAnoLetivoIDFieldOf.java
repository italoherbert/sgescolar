package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Feriado;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class FeriadoAnoLetivoIDFieldOf implements EntityFieldOf<Feriado, Long> {

	@Override
	public Expression<Long> exp(Root<Feriado> root) {
		return root.join( "anoLetivo" ).get( "id" );
	}

}