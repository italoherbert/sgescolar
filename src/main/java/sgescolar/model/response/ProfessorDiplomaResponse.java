package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorDiplomaResponse {

	private Long id;
	
	private String curso;
	
	private String titulacao;
	
}
