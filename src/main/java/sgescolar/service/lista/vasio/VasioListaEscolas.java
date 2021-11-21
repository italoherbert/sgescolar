package sgescolar.service.lista.vasio;

import java.util.ArrayList;
import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.lista.ListaEscolas;

public class VasioListaEscolas implements ListaEscolas {

	@Override
	public List<Escola> lista(EscolaRepository repository) {
		return new ArrayList<>();
	}

}
