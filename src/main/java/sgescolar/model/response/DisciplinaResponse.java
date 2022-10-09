package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaResponse {
	
	private Long id;
	
	private String descricao;
	
	private String sigla;
			
	private SerieResponse serie;
	
}
