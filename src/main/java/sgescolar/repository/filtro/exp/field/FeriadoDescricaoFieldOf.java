package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Feriado;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class FeriadoDescricaoFieldOf implements EntityFieldOf<Feriado, String> {

	@Override
	public Expression<String> exp( Root<Feriado> root ) {
		return root.get( "descricao" ); 
	}

}
