package sgescolar.util.string;

import java.text.Normalizer;

public class RemoveAcentoStringGerador implements StringGerador {

	@Override
	public String gera(String param) {
		return Normalizer.normalize( param, Normalizer.Form.NFD ).replaceAll( "[^\\p{ASCII}]", "" ); 
	}
	
}
