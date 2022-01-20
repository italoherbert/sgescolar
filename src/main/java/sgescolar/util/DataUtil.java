package sgescolar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DataUtil {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
	private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat( "dd/MM/yyyy hh:mm:ss" );
	private final String DATA_ZERO_STR = "00/00/0000";
				
	public Date dataHoje() {
		Calendar c1 = Calendar.getInstance();
				
		Calendar c = Calendar.getInstance();
		c.set( c1.get( Calendar.YEAR ), c1.get( Calendar.MONTH ), c1.get( Calendar.DAY_OF_MONTH ) );
		
		return c.getTime();
	}
				
	public Date dataZero() {
		try {
			return dateFormat.parse( DATA_ZERO_STR );
		} catch (ParseException e) {
			return null;
		}
	}
	
	public Date apenasData( Date data ) {
		String dstr = dateFormat.format( data );
		try {
			return dateFormat.parse( dstr );
		} catch (ParseException e) {
			return data;
		}		
	}
	
	public Date addDias( Date data, int ndias ) {
		Calendar c = Calendar.getInstance();
		c.setTime( data );
		
		c.add( Calendar.DATE, ndias );
		
		return c.getTime();
	}
	
	public int getSemanaDia( Date dataDia ) {
		Calendar c = Calendar.getInstance();
		c.setTime( dataDia );
		return c.get( Calendar.DAY_OF_WEEK );
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public SimpleDateFormat getDateTimeFormat() {
		return dateTimeFormat;
	}
	
}
