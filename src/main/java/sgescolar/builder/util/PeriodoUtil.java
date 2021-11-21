package sgescolar.builder.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PeriodoUtil {
	
	public int contaDiasLetivos( Date dataInicio, Date dataFim, List<Date> feriados ) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime( dataInicio );
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime( dataFim ); 

		Calendar c3 = Calendar.getInstance();
		
		int fsize = feriados.size();
		
		int cont = 0;
		while( c1.compareTo( c2 ) <= 0 ) {
			int dia = c1.get( Calendar.DAY_OF_WEEK );
			
			boolean ehDiaUtil = false;
			if( dia > 0 && dia < 6 ) {
				ehDiaUtil = true;
				for( int i = 0; ehDiaUtil && i < fsize; i++ ) {
					c3.setTime( feriados.get( i ) );
					if ( c1.compareTo( c3 ) == 0 )
						ehDiaUtil = false;
				}
			}
			
			if ( ehDiaUtil )
				cont++;
			
			c1.add( Calendar.DATE, 1 ); 
		}
		
		return cont;
	}		
	
}
