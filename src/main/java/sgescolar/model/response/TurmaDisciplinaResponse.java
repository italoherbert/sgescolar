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
	
	private DisciplinaResponse disciplina;
	
	private List<AulaResponse> aulas;
	
}
