package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HorarioResponse {

	private String quantidadeAulasDia;
	
	private List<HorarioAulaResponse> aulas;
	
}
