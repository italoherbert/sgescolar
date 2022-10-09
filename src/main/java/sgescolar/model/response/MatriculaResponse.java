package sgescolar.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MatriculaResponse {

	private Long id;
	
	private Long alunoId;
	
	private String alunoNome;
	
	private String numero;
	
	private String anoLetivoAno;
	
	private String dataInicio;
	
	private String dataEncerramento;
	
	private String encerrada;
	
	private TurmaResponse turma;
	
}
