package sgescolar.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sgescolar.msg.SistemaException;

@AllArgsConstructor
@Getter
@Setter
public class ErroResponse {
	
	private String mensagem;

	public ErroResponse( SistemaException e ) {
		this.mensagem = e.getMensagem();
	}
		
}
