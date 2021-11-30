package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAulaRequest {

	private Long turmaDisciplinaId;

	private String dia;
	
	private String numeroAula;
		
}
