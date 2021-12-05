package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ListaAlunoFrequenciaResponse {

	private Long id;
	
	private String dataDia;
			
	private TipoResponse turno;

	private HorarioAulaResponse horarioAula;

	private List<AlunoFrequenciaResponse> frequencias;
	
}
