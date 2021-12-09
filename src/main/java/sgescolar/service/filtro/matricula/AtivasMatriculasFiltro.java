package sgescolar.service.filtro.matricula;

import java.util.List;

import sgescolar.model.Matricula;
import sgescolar.repository.MatriculaRepository;
import sgescolar.service.filtro.MatriculasFiltro;

public class AtivasMatriculasFiltro implements MatriculasFiltro {

	@Override
	public List<Matricula> filtra(MatriculaRepository repository, Long turmaId, String nomeini) {
		return repository.filtraAtivas( turmaId, nomeini ); 
	}

}
