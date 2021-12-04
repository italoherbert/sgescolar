package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GrupoListaAlunoFrequenciaResponse {
	
	private List<MatriculaResponse> matriculas;
	
	private List<AulaResponse> aulas;
		
	private TipoResponse[] frequenciaTiposAula0;
	
	private String[][] estevePresenteMatriz;
	
	private String dataDia;
		
	private String matriculasQuant;
	
	private String aulasQuant;
	
}
