package sgescolar.service.filtra.secretario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.filtra.FiltroEscolas;

public class SecretarioFiltroEscolas implements FiltroEscolas {

	private final Long escolaId;
	
	public SecretarioFiltroEscolas(Long escolaId) {
		super();
		this.escolaId = escolaId;
	}

	@Override
	public List<Escola> filtra(EscolaRepository repository, String nome) {
		List<Escola> escolas = repository.filtra( nome );
		for( Escola e : escolas )
			if ( e.getId() == escolaId )
				return Arrays.asList( e );
		
		return new ArrayList<>();
	}

}
