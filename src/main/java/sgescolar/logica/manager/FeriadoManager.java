package sgescolar.logica.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import sgescolar.model.Feriado;

@Component
public class FeriadoManager {

	public List<Date> listaDiasFeriados( List<Feriado> feriados ) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		List<Date> lista = new ArrayList<>();
		for( Feriado f : feriados ) {
			c1.setTime( f.getDataInicio() );
			c2.setTime( f.getDataFim() );
			
			while( c1.compareTo( c2 ) <= 0 ) {
				lista.add( c1.getTime() );
				c1.add( Calendar.DATE, 1 ); 
			}
		}
		
		return lista;
	}
	
}
