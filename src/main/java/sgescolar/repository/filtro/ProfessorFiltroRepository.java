package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Professor;

public interface ProfessorFiltroRepository {

	public List<Professor> filtra( String nomeIni );
	
}
