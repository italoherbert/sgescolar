package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FuncionarioResponse {

	private Long id;

	private String codigoInep;
		
	private TipoResponse escolaridade;
			
	private TipoResponse funcao;

	private String cargaHoraria;
	
	private String escolaFunc;
	
	private UsuarioResponse usuario;
	
	private PessoaResponse pessoa;
		
}

