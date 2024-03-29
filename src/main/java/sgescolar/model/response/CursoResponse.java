package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CursoResponse {

	private Long id;
	
	private Long instituicaoId;

	private Long escolaId;
	
	private String escolaNome;
	
	private String descricao;
	
	private String quantidadeAulasDia;
	
	private String cargaHoraria;

	private TipoResponse modalidade;
	
	private TipoResponse avaliacaoMetodo;
				
}
