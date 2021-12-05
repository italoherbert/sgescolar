package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TurmaResponse {

	private Long id;
	
	private String descricao;
	
	private String descricaoDetalhada;
	
	private TipoResponse turno;
	
	private Long anoLetivoId;
	
	private String anoLetivoAno;
	
	private SerieResponse serie;
	
	private List<TurmaDisciplinaResponse> turmaDisciplinas;
	
}
