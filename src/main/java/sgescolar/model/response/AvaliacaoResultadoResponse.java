package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoResultadoResponse {

	private Long id;
	
	private MatriculaResponse matricula;
		
	private TipoResponse avaliacaoMetodo;
	
	private TipoResponse avaliacaoTipo;
	
	private String nota;
	
	private TipoResponse conceito;
	
	private String descricao;
	
}
