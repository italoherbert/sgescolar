package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaiOuMaeBuscadoResponse {

	private String pessoaEncontrada;
	
	private String pessoaPaiOuMaeEncontrado;
			
	private PessoaResponse pessoa;
	
	private PessoaPaiOuMaeResponse pessoaPaiOuMae;

}
