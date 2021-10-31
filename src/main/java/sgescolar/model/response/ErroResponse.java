package sgescolar.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErroResponse {
	
	private String mensagem;

	public ErroResponse( Exception e ) {
		this.mensagem = e.getMessage();
	}
		
}
