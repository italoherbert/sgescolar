package sgescolar.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Component;

@Component
public class ArquivoUtil {

	public void copia( InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[ 2048 ];
		int len;
		while( (len = in.read( buffer ) ) != -1 )
			out.write( buffer, 0, len );
		
		out.flush();		
		out.close();			
	} 	
}
