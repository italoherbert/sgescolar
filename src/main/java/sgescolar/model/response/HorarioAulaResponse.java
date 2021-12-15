package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HorarioAulaResponse {
	
	private Long id;
	
	private String semanaDia;
	
	private String semanaDiaLabel;
	
	private String numeroAula;
		
	private String disciplinaSigla;
	
	private String ativa;
	
}
