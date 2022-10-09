package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AlunoResponse {

	private Long id;
	
	private PessoaResponsavelResponse pai;
	
	private PessoaResponsavelResponse mae;
	
	private PessoaResponsavelResponse responsavel;
	
	private UsuarioResponse usuario;
	
	private PessoaResponse pessoa;
		
}
