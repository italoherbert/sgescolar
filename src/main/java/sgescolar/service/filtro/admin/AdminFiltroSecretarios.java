package sgescolar.service.filtro.admin;

import java.util.List;

import sgescolar.model.Secretario;
import sgescolar.repository.SecretarioRepository;
import sgescolar.service.filtro.FiltroSecretarios;

public class AdminFiltroSecretarios implements FiltroSecretarios {
			
	
	
	public List<Secretario> filtra( SecretarioRepository repository, String nomeIni ) {				
		return repository.filtra( nomeIni );
	}
}