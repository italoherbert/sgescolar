package sgescolar.service.filtra.admin;

import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.filtra.FiltroEscolas;

public class AdminFiltroEscolas implements FiltroEscolas {

	@Override
	public List<Escola> filtra(EscolaRepository repository, String nome) {
		return repository.filtra( nome ); 
	}

}
