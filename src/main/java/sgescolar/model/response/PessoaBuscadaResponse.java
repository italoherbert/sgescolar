package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PessoaBuscadaResponse {

	private String encontradaPessoa;
	
	private String encontradoPaiOuMae;
			
	private PessoaResponse pessoa;
	
	private PessoaPaiOuMaeResponse paiOuMae;

}
