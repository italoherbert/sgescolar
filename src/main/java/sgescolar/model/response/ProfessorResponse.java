package sgescolar.model.response;

import lombok.Setter;

import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorResponse {

	private Long id;
	
	private FuncionarioResponse funcionario;
	
}
