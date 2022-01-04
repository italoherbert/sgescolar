package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAvaliacaoResultadoRequest {

	private Long matriculaId;
		
	private String avaliacaoTipo;
	
	private String resultado;
	
}
