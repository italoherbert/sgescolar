package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAgendamentoAvaliacaoRequest {
	
	private Long periodoId;

	private String peso;
	
	private String dataAgendamento;
		
}
