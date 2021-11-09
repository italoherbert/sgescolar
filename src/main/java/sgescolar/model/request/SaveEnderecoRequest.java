package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveEnderecoRequest {

	private String logradouro;
	
	private String complemento;
	
	private String bairro;
	
	private String municipio;
	
	private String uf;
	
	private String cep;
	
}
