package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdministradorResponse {

	private Long id;
	
	private FuncionarioResponse funcionario;
	
	private InstituicaoResponse instituicao;
	
}
