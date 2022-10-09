package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorResponse {

	private Long id;
	
	private FuncionarioResponse funcionario;
	
	private List<ProfessorDiplomaResponse> diplomas;
	
}
