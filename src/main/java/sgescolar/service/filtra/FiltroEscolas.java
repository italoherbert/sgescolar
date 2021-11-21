package sgescolar.service.filtra;

import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;

public interface FiltroEscolas {

	public List<Escola> filtra( EscolaRepository repository, String nome );
	
}

