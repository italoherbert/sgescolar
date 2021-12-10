package sgescolar.model.request.filtro;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FiltraCursosRequest {

	private String descricaoIni;
	
	private String modalidade;
	
	private String escolaId;
	
}

