package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PessoaMaeOuPaiResponse {

	private Long id;
	
	private String falecido;
	
	private PessoaResponse pessoa;
	
}
