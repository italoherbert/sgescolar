package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EnderecoLocalResponse {

	private Long id;
	
	private String logradouro;
	
	private String complemento;
	
	private String bairro;
	
	private String cep;
	
}
