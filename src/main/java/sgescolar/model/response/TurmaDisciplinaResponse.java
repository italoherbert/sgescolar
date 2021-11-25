package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TurmaDisciplinaResponse {
	
	private TurmaResponse turma;
	
	private DisciplinaResponse disciplina;
	
}
