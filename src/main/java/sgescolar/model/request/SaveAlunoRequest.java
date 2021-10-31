package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAlunoRequest {

	private SavePessoaMaeOuPaiRequest pai;
	
	private SavePessoaMaeOuPaiRequest mae;
	
	private SaveUsuarioRequest usuario;
	
	private SavePessoaRequest pessoa;
	
}
