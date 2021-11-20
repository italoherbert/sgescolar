package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BimestreResponse {

	private Long id;
	
	private String dataInicio;
	
	private String dataFim;
	
	private List<DiaLetivoResponse> anosLetivos;
	
}
