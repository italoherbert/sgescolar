package sgescolar.repository.filtro.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import sgescolar.repository.filtro.exp.EntityFieldOf;
import sgescolar.repository.filtro.exp.StringExpressionFilter;
import sgescolar.repository.filtro.exp.filter.RemoveAcentoStringExpressionFilter;
import sgescolar.util.string.RemoveAcentoStringGerador;
import sgescolar.util.string.StringGerador;

public class LikeIgnoreCaseEAcentoStringSpecification<T extends Object> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	private final StringExpressionFilter stringExpressionFilter = new RemoveAcentoStringExpressionFilter();
	private final StringGerador removeAcentoStrgen = new RemoveAcentoStringGerador();

	private final EntityFieldOf<T, String> stringEntityField;
	private final String param;
		
	public LikeIgnoreCaseEAcentoStringSpecification( EntityFieldOf<T, String> entityField, String param ) {		
		this.stringEntityField = entityField;
		this.param = param;
	}

	@Override
	public Predicate toPredicate( Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder ) {
		String semAcentoParam = removeAcentoStrgen.gera( param.toLowerCase() );
		
        Expression<String> exp = stringEntityField.exp( root );  
        exp = stringExpressionFilter.novaExp( criteriaBuilder, exp );        
        exp = criteriaBuilder.lower( exp );

        return criteriaBuilder.like( exp, criteriaBuilder.literal( semAcentoParam ) );
	}

}
