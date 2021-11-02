package sgescolar.service.filtro;

import java.util.List;

import sgescolar.model.Secretario;
import sgescolar.repository.SecretarioRepository;

public interface FiltroSecretarios {

	public List<Secretario> filtra( SecretarioRepository repository, String nomeIni );
	
}
