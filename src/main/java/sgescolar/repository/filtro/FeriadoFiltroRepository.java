package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Feriado;

public interface FeriadoFiltroRepository {

	public List<Feriado> filtra( Long anoLetivoId, String descricaoIni );

}
