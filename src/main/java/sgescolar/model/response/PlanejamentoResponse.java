package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlanejamentoResponse {

	private Long id;
	
	private String metodologia;
	
	private String recursos;
	
	private String metodosAvaliacao;
	
	private String referencias;
	
	private String dataInicio;
	
	private String dataFim;
	
	private List<PlanejamentoObjetivoResponse> objetivos;
	
	private List<PlanejamentoConteudoResponse> conteudos;
	
	private List<PlanejamentoAnexoResponse> anexos;
	
}
