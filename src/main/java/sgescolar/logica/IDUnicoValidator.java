package sgescolar.logica;

import java.util.ArrayList;
import java.util.List;

public class IDUnicoValidator {

	private List<Long> ids = new ArrayList<>();
	
	public boolean verificaSeUnico( Long id ) {
		boolean unico = true;
		int size = ids.size();
		for( int i = 0; unico && i < size; i++ )
			if ( ids.get( i ) == id )
				unico = false;
		
		if ( unico )			
			ids.add( id );
		
		return unico;
	}
	
}


