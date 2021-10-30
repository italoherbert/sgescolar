package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InstituicaoResponse {

	private String cnpj;
	
	private String razaoSocial;
	
	private EnderecoResponse endereco;
	
	private ContatoInfoResponse contatoInfo;
	
}
