package sgescolar.service.lista.secretario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sgescolar.model.Escola;
import sgescolar.repository.EscolaRepository;
import sgescolar.service.lista.ListaEscolas;

public class SecretarioListaEscolas implements ListaEscolas {

	private final Long escolaId;
	
	public SecretarioListaEscolas( Long escolaId ) {
		this.escolaId = escolaId;
	}

	@Override
	public List<Escola> lista( EscolaRepository repository ) {
		List<Escola> lista = new ArrayList<>();
		
		Optional<Escola> eop = repository.findById( escolaId );
		if ( eop.isPresent() )
			lista.add( eop.get() );
		
		return lista;
	}

}
