package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SerieResponse {

	private Long id;
	
	private String descricao;
	
	private String grau;
	
	private Long cursoId;

	private String cursoNome;

	private Long escolaId;
	
	private String escolaNome;
	
}
