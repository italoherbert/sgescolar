package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PessoaPaiOuMaeResponse {

	private Long id;
	
	private String desconhecido;
	
	private String falecido;
	
	private PessoaResponse pessoa;
	
}
