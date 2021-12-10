package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArquivoResponse {

	private Long id;
	
	private String nome;
	
	private byte[] dados;
	
}
