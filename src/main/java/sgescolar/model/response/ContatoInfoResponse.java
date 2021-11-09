package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContatoInfoResponse {
	
	private Long id;
	
	private String telefoneFixo;

	private String telefoneCelular;
	
	private String email;	
	
}
