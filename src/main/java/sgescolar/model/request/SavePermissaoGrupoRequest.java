package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SavePermissaoGrupoRequest {
	
	private String recurso;
	
	private String leitura;
	
	private String escrita;
	
	private String remocao;
	
}
