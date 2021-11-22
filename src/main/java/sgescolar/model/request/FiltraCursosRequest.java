package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FiltraCursosRequest {

	private String nomeIni;
	
	private String modalidade;
	
	private String escolaId;
	
}

