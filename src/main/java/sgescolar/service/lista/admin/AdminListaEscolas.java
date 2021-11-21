package sgescolar.service.lista.admin;

import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.lista.ListaEscolas;

public class AdminListaEscolas implements ListaEscolas {

	@Override
	public List<Escola> lista( EscolaRepository repository ) {
		return repository.findAll();
	}

}
