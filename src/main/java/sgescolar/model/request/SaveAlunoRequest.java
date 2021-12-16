package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAlunoRequest {
	
	private SaveUsuarioRequest usuario;
	
	private SavePessoaRequest pessoa;

	private SavePessoaResponsavelRequest pai;
	
	private SavePessoaResponsavelRequest mae;
	
	private SavePessoaResponsavelRequest responsavel;
	
}
