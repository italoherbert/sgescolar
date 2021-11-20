package sgescolar.model.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SaveAnoLetivoRequest {

	private String escolaId;
	
	private String ano;
	
	private SaveBimestreRequest primeiroBimestre;
	
	private SaveBimestreRequest segundoBimestre;
	
	private SaveBimestreRequest terceiroBimestre;

	private SaveBimestreRequest quartoBimestre;
	
	private List<SaveFeriadoRequest> feriados;
	
}
