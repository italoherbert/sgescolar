package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AlunoResponse {

	private Long id;
	
	private PessoaPaiOuMaeResponse pai;
	
	private PessoaPaiOuMaeResponse mae;
	
	private UsuarioResponse usuario;
	
	private PessoaResponse pessoa;
	
}
