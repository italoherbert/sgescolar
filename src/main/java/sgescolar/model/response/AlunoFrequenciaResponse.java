package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AlunoFrequenciaResponse {

	private Long id;

	private String estevePresente;

	private MatriculaResponse matricula;
				
	private TipoResponse frequenciaTipo;
	
}
