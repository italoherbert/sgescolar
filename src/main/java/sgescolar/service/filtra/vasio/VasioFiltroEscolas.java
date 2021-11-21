package sgescolar.service.filtra.vasio;

import java.util.ArrayList;
import java.util.List;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.filtra.FiltroEscolas;

public class VasioFiltroEscolas implements FiltroEscolas {

	@Override
	public List<Escola> filtra(EscolaRepository repository, String nomeIni) {
		return new ArrayList<>(0); 
	}

}
