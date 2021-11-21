package sgescolar.service.filtra.vasio;

import java.util.ArrayList;
import java.util.List;

import sgescolar.model.Secretario;
import sgescolar.repository.SecretarioRepository;
import sgescolar.service.filtra.FiltroSecretarios;

public class VasioFiltroSecretarios implements FiltroSecretarios {

	@Override
	public List<Secretario> filtra(SecretarioRepository repository, String nomeIni) {
		return new ArrayList<>(0); 
	}

}
