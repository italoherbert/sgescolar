package sgescolar.util;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {

	@Autowired
	private DataUtil dataUtil;
	
	public boolean dataValida( String data ) {
		try {
			dataUtil.getDateFormat().parse( data );
			return true;
		} catch ( ParseException e ) {
			return false;
		}
	}
	
	public boolean dataHoraValida( String dataHora ) {
		try {
			dataUtil.getDateTimeFormat().parse( dataHora );
			return true;
		} catch ( ParseException e ) {
			return false;
		}
	}
	
	public boolean doubleValido( String valor ) {
		try {
			Double.parseDouble( valor ); 
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public boolean intValido( String valor ) {
		try {
			Integer.parseInt( valor );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
	public boolean booleanValido( String valor ) {
		return valor.equals( "true" ) || valor.equals( "false" );			
	}
	
}
