package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveEnderecoLocalRequest {

	private String logradouro;
	
	private String complemento;
	
	private String bairro;		
	
	private String cep;
	
}