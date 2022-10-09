package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAvaliacaoAgendamentoRequest {
	
	private Long periodoId;
		
	private String dataAgendamento;
	
	private String avaliacaoTipo;
	
	private String peso;
		
}
