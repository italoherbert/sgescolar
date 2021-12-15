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
	
	private Long anoLetivoId;

	private String descricao;
	
	private String descricaoDetalhada;
			
	private String anoLetivoAno;
	
	private String quantidadeAulasDia;
	
	private TipoResponse turno;

	private SerieResponse serie;
	
	private List<TurmaDisciplinaResponse> turmaDisciplinas;
	
}
