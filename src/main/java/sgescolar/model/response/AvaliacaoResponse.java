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
	
	private String notasDisponiveis;
	
	private TurmaDisciplinaResponse turmaDisciplina;
			
	private List<NotaResponse> notas;
	
}
