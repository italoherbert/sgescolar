package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.enums.tipos.CursoModalidade;
import sgescolar.model.Curso;

public interface CursoFiltroRepository {

	public List<Curso> filtra( Long escolaId, String nomeIni, CursoModalidade modalidade );
	
}
