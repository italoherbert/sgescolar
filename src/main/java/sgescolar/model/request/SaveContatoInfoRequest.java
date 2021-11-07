package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveContatoInfoRequest {
	
	private String telefoneFixo;
	
	private String telefoneCelular;

	private String email;	
	
}
