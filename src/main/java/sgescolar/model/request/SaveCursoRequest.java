package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveCursoRequest {

	private String descricao;
	
	private String modalidade;
	
	private String cargaHoraria;
	
	private String quantidadeAulasDia;
	
	private String avaliacaoMetodo;
	
}
