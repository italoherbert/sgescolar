package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveEscolaRequest {

	private String nome;
	
	private SaveEnderecoRequest endereco	;
	
	private SaveContatoInfoRequest contatoInfo;
	
}
