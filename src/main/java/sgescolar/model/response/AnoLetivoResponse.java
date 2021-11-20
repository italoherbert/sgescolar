package sgescolar.model.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnoLetivoResponse {

	private Long id;
	
	private String ano;
	
	private BimestreResponse primeiroBimestre;
	
	private BimestreResponse segundoBimestre;
	
	private BimestreResponse terceiroBimestre;
	
	private BimestreResponse quartoBimestre;
	
	private List<FeriadoResponse> feriados;
	
}
