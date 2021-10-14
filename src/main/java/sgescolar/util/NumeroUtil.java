package sgescolar.util;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import sgescolar.exception.DoubleInvalidoException;
import sgescolar.exception.InteiroInvalidoException;
import sgescolar.exception.LongInvalidoException;

@Component
public class NumeroUtil {
	
	private DecimalFormat df = new DecimalFormat( "R$ 0.00" );
	
	public String formatoReal( double valor ) {
		return df.format( valor );
	}
	
	public String doubleParaString( double numero ) {
		return String.valueOf( numero );
	}
	
	public double stringParaDouble( String numero ) throws DoubleInvalidoException {
		try {
			return Double.parseDouble( numero );
		} catch ( NumberFormatException e ) {
			throw new DoubleInvalidoException();
		}		
	}
	
	public String inteiroParaString( int numero ) {
		return String.valueOf( numero );
	}
	
	public int stringParaInteiro( String numero ) throws InteiroInvalidoException {
		try {
			return Integer.parseInt( numero );
		} catch ( NumberFormatException e ) {
			throw new InteiroInvalidoException();
		}
	}
	
	public String longParaString( int numero ) {
		return String.valueOf( numero );
	}
	
	public Long stringParaLong( String numero ) throws LongInvalidoException {
		try {
			return Long.parseLong( numero );
		} catch ( NumberFormatException e ) {
			throw new LongInvalidoException();
		}
	}
	
}
