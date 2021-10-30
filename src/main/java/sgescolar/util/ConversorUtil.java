package sgescolar.util;

import org.springframework.stereotype.Component;

import sgescolar.exception.BooleanFormatoException;
import sgescolar.exception.DoubleFormatoException;
import sgescolar.exception.InteiroFormatoException;
import sgescolar.exception.LongFormatoException;

@Component
public class ConversorUtil {
				
	public String doubleParaString( double numero ) {
		return String.valueOf( numero );
	}
	
	public double stringParaDouble( String numero ) throws DoubleFormatoException {
		try {
			return Double.parseDouble( numero );
		} catch ( NumberFormatException e ) {
			throw new DoubleFormatoException();
		}		
	}
	
	public String inteiroParaString( int numero ) {
		return String.valueOf( numero );
	}
	
	public int stringParaInteiro( String numero ) throws InteiroFormatoException {
		try {
			return Integer.parseInt( numero );
		} catch ( NumberFormatException e ) {
			throw new InteiroFormatoException();
		}
	}
	
	public String longParaString( int numero ) {
		return String.valueOf( numero );
	}
	
	public Long stringParaLong( String numero ) throws LongFormatoException {
		try {
			return Long.parseLong( numero );
		} catch ( NumberFormatException e ) {
			throw new LongFormatoException();
		}
	}
	
	public boolean stringParaBoolean( String valor ) throws BooleanFormatoException {
		if ( valor.equals( "true" ) || valor.equals( "false" ) )
			return Boolean.parseBoolean( valor );
		
		throw new BooleanFormatoException();
	}
	
	public String booleanParaString( boolean valor ) {
		return String.valueOf( valor );
	}
	
}
