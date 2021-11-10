package sgescolar.repository.filtro.exp;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public interface StringExpressionFilter { 
	
	public Expression<String> novaExp( CriteriaBuilder criteriaBuilder, Expression<String> exp );
	
}
