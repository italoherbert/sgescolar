package sgescolar.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversorUtil {
	
	@Autowired
	private DataUtil dataUtil;
	
	private DecimalFormat df = new DecimalFormat( "R$ 0.00" );
	
	public String formatoReal( double valor ) {
		return df.format( valor );
	}
	
	public String dataParaString( Date date ) {
		return dataUtil.getDateFormat().format( date );
	}
	
	public Date stringParaData( String data ) {
		try {
			return dataUtil.getDateFormat().parse( data );
		} catch (ParseException e) {
			return dataUtil.dataZero();			
		}		
	}
	
	public String dataHoraParaString( Date data ) {
		return dataUtil.getDateTimeFormat().format( data );
	}
		
	public String doubleParaString( double numero ) {
		return String.valueOf( numero );
	}
	
	public double stringParaDouble( String numero ) {
		try {
			return Double.parseDouble( numero );
		} catch ( NumberFormatException e ) {
			return 0;
		}		
	}
	
	public String inteiroParaString( int numero ) {
		return String.valueOf( numero );
	}
	
	public int stringParaInteiro( String numero ) {
		try {
			return Integer.parseInt( numero );
		} catch ( NumberFormatException e ) {
			return 0;
		}
	}
	
	public String longParaString( long numero ) {
		return String.valueOf( numero );
	}
	
	public long stringParaLong( String numero ) {
		try {
			return Long.parseLong( numero );
		} catch ( NumberFormatException e ) {
			return 0;
		}
	}
	
	public boolean stringParaBoolean( String valor ) {
		if ( valor.equals( "true" ) || valor.equals( "false" ) )
			return Boolean.parseBoolean( valor );		
		return false;
	}
	
	public String booleanParaString( boolean valor ) {
		return String.valueOf( valor );
	}
	
}
