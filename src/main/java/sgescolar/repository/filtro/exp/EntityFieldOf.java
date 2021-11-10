package sgescolar.repository.filtro.exp;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface EntityFieldOf<T extends Object, T2 extends Object> {
	
	public Expression<T2> exp( Root<T> root );

}
