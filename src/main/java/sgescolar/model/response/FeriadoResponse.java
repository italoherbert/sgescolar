package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FeriadoResponse {

	private Long id;
	
	private String descricao;
	
	private String dataInicio;
	
	private String dataFim;
	
}
