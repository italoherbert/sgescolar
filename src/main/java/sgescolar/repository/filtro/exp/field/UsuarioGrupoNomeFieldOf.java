package sgescolar.repository.filtro.exp.field;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import sgescolar.model.UsuarioGrupo;
import sgescolar.repository.filtro.exp.EntityFieldOf;

public class UsuarioGrupoNomeFieldOf implements EntityFieldOf<UsuarioGrupo, String> {

	@Override
	public Expression<String> exp( Root<UsuarioGrupo> root ) {
		return root.get( "nome" );  
	}

}
