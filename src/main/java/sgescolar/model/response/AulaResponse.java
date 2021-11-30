package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AulaResponse {
	
	private Long id;
	
	private String dia;
	
	private String numeroAula;
	
	private String disciplinaSigla;
	
}
