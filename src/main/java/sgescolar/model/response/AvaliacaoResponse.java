package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoResponse {

	private Long id;

	private String peso;
	
	private String dataAgendamento;
			
	private String resultadoDisponivel;
	
	private TipoResponse avaliacaoTipo;
	
	private TipoArrayResponse conceitoTipos;
	
	private TurmaDisciplinaResponse turmaDisciplina;
			
	private List<AvaliacaoResultadoResponse> resultados;
	
	private PeriodoResponse periodo;
	
}
