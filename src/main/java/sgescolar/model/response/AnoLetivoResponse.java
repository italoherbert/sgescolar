package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnoLetivoResponse {

	private Long id;
	
	private String ano;
	
	private Long escolaId;
	
	private String escolaNome;
			
	private List<PeriodoResponse> periodos;
	
	private List<FeriadoResponse> feriados;
	
}
