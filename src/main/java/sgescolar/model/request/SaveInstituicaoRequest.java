package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveInstituicaoRequest {

	private String cnpj;
	
	private String razaoSocial;
	
	private SaveEnderecoRequest endereco;
	
	private SaveContatoInfoRequest contatoInfo;
	
}
