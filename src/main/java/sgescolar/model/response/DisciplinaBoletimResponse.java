package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaBoletimResponse {

	private String disciplinaDescricao;		
	
	private List<String> notas;
	
	private List<String> pesos;
	
	private String media;
	
}
