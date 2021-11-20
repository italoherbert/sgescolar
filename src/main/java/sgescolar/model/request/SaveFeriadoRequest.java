package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveFeriadoRequest {

	private String descricao;
	
	private String dataInicio;
	
	private String dataFim;
	
}
