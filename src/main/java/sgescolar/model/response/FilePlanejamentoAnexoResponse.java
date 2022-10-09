package sgescolar.model.response;

import java.sql.Blob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FilePlanejamentoAnexoResponse {

	private Long id;
	
	private String arquivoNome;
	
	private Blob arquivo;
	
}
