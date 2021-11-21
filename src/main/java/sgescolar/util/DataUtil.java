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
	
	public Date addUmDia( Date data ) {
		Calendar c = Calendar.getInstance();
		c.setTime( data );
		
		c.add( Calendar.DATE, 1 );
		
		return c.getTime();
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public SimpleDateFormat getDateTimeFormat() {
		return dateTimeFormat;
	}
	
}
