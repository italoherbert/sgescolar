package sgescolar.service.lista;

import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;

public interface ListaEscolas {
	
	public List<Escola> lista( EscolaRepository repository );
	
}
