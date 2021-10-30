package sgescolar.util;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class FormatadorUtil {

	private DecimalFormat df = new DecimalFormat( "R$ 0.00" );
	
	public String formatoReal( double valor ) {
		return df.format( valor );
	}
			
}
