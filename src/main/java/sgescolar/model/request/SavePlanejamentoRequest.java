package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SavePlanejamentoRequest {

	private String metodologia;
	
	private String recursos;
	
	private String metodosAvaliacao;
	
	private String referencias;
	
	private String dataInicio;
	
	private String dataFim;
	
	private List<SavePlanejamentoObjetivoRequest> objetivos;
	
	private List<SavePlanejamentoConteudoRequest> conteudos;
	
	private List<SavePlanejamentoAnexoRequest> anexos;
	
}
