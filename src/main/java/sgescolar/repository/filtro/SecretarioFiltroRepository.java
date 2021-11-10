package sgescolar.repository.filtro;

import java.util.List;

import sgescolar.model.Secretario;

public interface SecretarioFiltroRepository {

	public List<Secretario> filtra( Long escolaId, String nomeIni );

	public List<Secretario> filtra( String nomeIni );
	
}
