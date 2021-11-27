package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FiltraInstituicaoRequest {

	private String cnpj;
	
	private String razaoSocialIni;
	
}
