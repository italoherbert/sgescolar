package sgescolar.service.filtro.secretario;

import java.util.List;

import sgescolar.model.Secretario;
import sgescolar.repository.SecretarioRepository;
import sgescolar.service.filtro.FiltroSecretarios;

public class SecretarioFiltroSecretarios implements FiltroSecretarios {
		
	private final Long escolaId;
	
	public SecretarioFiltroSecretarios( Long escolaId ) {
		this.escolaId = escolaId;
	}
	
	public List<Secretario> filtra( SecretarioRepository repository, String nomeIni ) {			
		return repository.filtra( escolaId, nomeIni );
	}
	
}
