package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoletimAvaliacaoResponse {

	private String dataAvaliacao;
		
	private String resultado;
	
	private String peso;
	
	private TipoResponse avaliacaoTipo;
	
}
