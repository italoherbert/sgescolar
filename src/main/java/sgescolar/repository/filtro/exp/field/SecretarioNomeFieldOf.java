package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Secretario;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class SecretarioNomeFieldOf implements EntityFieldOf<Secretario, String> {

	@Override
	public Expression<String> exp( Root<Secretario> root ) {
		return root.join( "funcionario" ).join( "pessoa" ).get( "nome" );  
	}

}