package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveCursoRequest {

	private String nome;
	
	private String modalidade;
	
	private String cargaHoraria;
		
}
