package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PeriodoLetivoResponse {

	private Long id;
	
	private String dataInicio;
	
	private String dataFim;
	
	private String lancamentoDataInicio;
	
	private String lancamentoDataFim;
	
	private String tipo;
		
}
