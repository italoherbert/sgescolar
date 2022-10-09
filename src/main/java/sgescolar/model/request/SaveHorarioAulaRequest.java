package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveHorarioAulaRequest {

	private Long turmaDisciplinaId;

	private String semanaDia;
			
	private String numeroAula;
		
}
