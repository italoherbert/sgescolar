package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class SaveListaAlunoFrequenciaRequest {

	private String dataDia;
	
	private String turno;
	
	private String numeroAula;
	
	private List<SaveAlunoFrequenciaRequest> frequencias;
	
}
