package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TurmaResponse {

	private Long id;
	
	private String descricao;
	
	private Long anoLetivoId;
	
	private String anoLetivoAno;
	
	private SerieResponse serie;
	
}
