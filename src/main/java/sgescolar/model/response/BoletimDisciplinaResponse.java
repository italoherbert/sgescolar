package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoletimDisciplinaResponse {

	private String disciplinaDescricao;		
	
	private List<BoletimAvaliacaoResponse> avaliacoes;
		
	private String media;
		
}
