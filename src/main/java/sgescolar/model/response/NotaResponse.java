package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotaResponse {

	private Long id;
	
	private MatriculaResponse matricula;
		
	private String nota;
	
}
