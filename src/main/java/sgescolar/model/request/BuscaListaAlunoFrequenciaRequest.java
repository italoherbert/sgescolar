package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BuscaListaAlunoFrequenciaRequest {

	private String dataDia;
	
	private String numeroAula;
	
}
