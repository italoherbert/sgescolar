package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.Aluno;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class AlunoNomeFieldOf implements EntityFieldOf<Aluno, String> {

	@Override
	public Expression<String> exp( Root<Aluno> root ) {
		return root.join( "pessoa" ).get( "nome" ); 
	}

}
