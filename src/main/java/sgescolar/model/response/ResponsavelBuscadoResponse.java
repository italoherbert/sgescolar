package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponsavelBuscadoResponse {

	private String pessoaEncontrada;
	
	private String pessoaResponsavelEncontrada;
			
	private PessoaResponse pessoa;
	
	private PessoaResponsavelResponse pessoaResponsavel;

}
