package sgescolar.repository.filtro.exp.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import sgescolar.repository.filtro.exp.StringExpressionFilter;

public class RemoveAcentoStringExpressionFilter implements StringExpressionFilter {

	public final static String COM_ACENTO = "áãàâäçéèëêùûüúóôöïîíÁÀÂÄÃÇÉÈËÊÙÛÜÚÓÔÖÏÎÍ";
	public final static String SEM_ACENTO = "aaaaaceeeeuuuuoooiiiAAAAACEEEEUUUUOOOIII";		
		
	@Override
	public Expression<String> novaExp( CriteriaBuilder criteriaBuilder, Expression<String> exp ) {
        for (int i = 0; i < COM_ACENTO.length(); i++) {
        	char comOuSemAcentoCH = COM_ACENTO.charAt( i );
        	char semAcentoCH = SEM_ACENTO.charAt( i );
        	
        	Expression<Character> comOuSemAcentoLiteralCH = criteriaBuilder.literal( comOuSemAcentoCH );
        	Expression<Character> semAcentoLiteralCH = criteriaBuilder.literal( semAcentoCH );
            exp = criteriaBuilder.function( "REPLACE", String.class, exp, comOuSemAcentoLiteralCH, semAcentoLiteralCH );
        }
        return exp;
	}

}
