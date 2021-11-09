package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveFuncionarioRequest {

	private String codigoInep;
		
	private String escolaridade;
		
	private String cargaHoraria;
	
	private String escolaFunc;
	
	private SaveUsuarioRequest usuario;
	
	private SavePessoaRequest pessoa;
		
}
