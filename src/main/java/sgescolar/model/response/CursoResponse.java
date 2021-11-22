package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CursoResponse {

	private Long id;
	
	private String nome;
	
	private String modalidade;
	
	private String cargaHoraria;
	
	private Long escolaId;
	
	private String escolaNome;
		
}
