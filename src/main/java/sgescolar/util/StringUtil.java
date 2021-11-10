package sgescolar.util;

import java.text.Normalizer;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

	public String removeAcentos( String str ) {
		return Normalizer.normalize( str, Normalizer.Form.NFD ).replaceAll( "[^\\p{ASCII}]", "" );
	}
	
}
