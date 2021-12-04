package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GrupoListaAlunoFrequenciaResponse {

	private List<ListaAlunoFrequenciaResponse> frequenciaListas;
	
	private List<MatriculaResponse> matriculas;
	
	private List<AulaResponse> aulas;
	
	private String temUmaOuMais;
	
}
