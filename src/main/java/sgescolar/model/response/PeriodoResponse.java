package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PeriodoResponse {

	private Long id;
	
	private String descricao;
	
	private String dataInicio;
	
	private String dataFim;
	
	private String lancamentoDataInicio;
	
	private String lancamentoDataFim;
	
	private String diasLetivosQuant;
	
	private String tipo;
		
}
