package sgescolar.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.msg.SistemaErro;
import sgescolar.msg.SistemaException;

@Component
public class ObjectMapperUtil {
		
	public SavePlanejamentoRequest novoPlanejamentoRequest( String json ) throws SistemaException {
		try {
			return new ObjectMapper().readValue( json.getBytes(), SavePlanejamentoRequest.class );			
		} catch (StreamReadException e) {
			e.printStackTrace();
			throw new SistemaException( SistemaErro.JSON_TO_CLASS_ERRO );
		} catch (DatabindException e) {
			e.printStackTrace();
			throw new SistemaException( SistemaErro.JSON_TO_CLASS_ERRO );
		} catch (IOException e) {
			e.printStackTrace();
			throw new SistemaException( SistemaErro.IO_ERRO );
		}
	}
	
}
