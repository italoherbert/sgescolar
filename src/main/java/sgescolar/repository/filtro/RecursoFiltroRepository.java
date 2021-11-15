package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Recurso;

public interface RecursoFiltroRepository {

	public List<Recurso> filtra( String nomeIni );

}
