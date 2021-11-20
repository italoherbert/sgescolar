package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveBimestreRequest {

	private String dataInicio;
	
	private String dataFim;
	
	private String lancamentoDataInicio;
	
	private String lancamentoDataFim;
	
	private List<SaveDiaLetivoRequest> diasLetivos;
	
}
