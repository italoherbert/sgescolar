package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PessoaResponsavelResponse {

	private Long id;
	
	private String registrado;
			
	private PessoaResponse pessoa;

}
