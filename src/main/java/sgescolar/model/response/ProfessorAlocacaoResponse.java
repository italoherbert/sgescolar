package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorAlocacaoResponse {

	private Long id;
	
	private Long professorId;
	
	private String professorNome;
	
	private String dataInicio;
	
	private String dataFim;
	
	private String ativo;
	
	private TurmaDisciplinaResponse turmaDisciplina;
	
}
