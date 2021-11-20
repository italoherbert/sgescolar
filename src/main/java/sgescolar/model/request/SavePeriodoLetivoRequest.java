package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SavePeriodoLetivoRequest {
	
	private String anoLetivoId;

	private String dataInicio;
	
	private String dataFim;
	
	private String lancamentoDataInicio;
	
	private String lancamentoDataFim;
	
	private String tipo;
			
}
