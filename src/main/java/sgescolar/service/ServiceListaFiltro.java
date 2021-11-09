package sgescolar.service;

import java.util.List;

public interface ServiceListaFiltro<T extends Object, T2 extends Object> {

	public List<T> filtra( T2 filtraObj );
	
}
