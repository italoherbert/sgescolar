package sgescolar.util;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {

	@Autowired
	private DataUtil dataUtil;
		
	public boolean dataValida( String data ) {
		if ( data == null )
			return false;
		
		try {
			dataUtil.getDateFormat().parse( data );
			return true;
		} catch ( ParseException e ) {
			return false;
		}
	}
	
	public boolean dataHoraValida( String dataHora ) {
		if ( dataHora == null )
			return false;
		
		try {
			dataUtil.getDateTimeFormat().parse( dataHora );
			return true;
		} catch ( ParseException e ) {
			return false;
		}
	}
	
	public boolean doubleValido( String valor ) {
		if ( valor == null )
			return false;
		
		try {
			Double.parseDouble( valor ); 
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public boolean intValido( String valor ) {
		if ( valor == null )
			return false;
		
		try {
			Integer.parseInt( valor );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public boolean longValido( String valor ) {
		if ( valor == null )
			return false;
		
		try {
			Long.parseLong( valor );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public boolean booleanValido( String valor ) {
		if ( valor == null )
			return false;
		
		return valor.equals( "true" ) || valor.equals( "false" );
	}
	
}
