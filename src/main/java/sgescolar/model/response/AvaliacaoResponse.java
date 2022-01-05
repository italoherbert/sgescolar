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
	
	private TipoArrayResponse conceitoTipos;
	
	private TipoResponse avaliacaoMetodo;
	
	private TipoResponse avaliacaoTipo;
	
	private TurmaDisciplinaResponse turmaDisciplina;
			
	private List<AvaliacaoResultadoResponse> resultados;
	
	private PeriodoResponse periodo;
	
}
