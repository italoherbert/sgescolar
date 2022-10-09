package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveProfessorRequest {

	private SaveFuncionarioRequest funcionario;
	
	private List<SaveProfessorDiplomaRequest> diplomas;
	
}
