package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class SaveListaAlunoFrequenciaRequest {

	private String horarioAulaId;

	private String dataDia;
				
	private List<SaveAlunoFrequenciaRequest> frequencias;
	
}
