package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAvaliacaoResultadoGrupoRequest {

	private List<SaveAvaliacaoResultadoRequest> resultados;
	
}
