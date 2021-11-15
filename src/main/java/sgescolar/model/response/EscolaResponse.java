package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EscolaResponse {

	private Long id;
	
	private String nome;
	
	private EnderecoLocalResponse enderecoLocal;
	
	private ContatoInfoResponse contatoInfo;
	
	private InstituicaoResponse instituicao;
	
}
