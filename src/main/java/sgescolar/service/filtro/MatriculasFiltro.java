package sgescolar.service.filtro;

import java.util.List;

import sgescolar.model.Matricula;
import sgescolar.repository.MatriculaRepository;

public interface MatriculasFiltro {

	public List<Matricula> filtra( MatriculaRepository repository, Long turmaId, String nomeini );
	
}
