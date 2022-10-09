package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TurmaDisciplinaResponse {
	
	private Long id;
	
	private Long turmaId;
	
	private Long serieId;
	
	private Long cursoId;
	
	private Long disciplinaId;
		
	private String turmaDescricaoDetalhada;
	
	private String turmaDescricao;
	
	private String serieDescricao;
	
	private String cursoDescricao;
		
	private String disciplinaDescricao;
	
	private String disciplinaSigla;
	
	private List<HorarioAulaResponse> horarioAulas;
	
}
