package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Escola;

public interface EscolaFiltroRepository {

	public List<Escola> filtra( String nomeIni );
	
}
