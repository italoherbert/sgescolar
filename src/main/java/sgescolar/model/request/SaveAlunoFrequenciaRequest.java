package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAlunoFrequenciaRequest {
	
	private Long matriculaId;

	private String estevePresente;
	
	private String frequenciaTipo;
		
}
