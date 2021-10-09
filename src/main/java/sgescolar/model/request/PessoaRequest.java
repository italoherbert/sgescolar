package sgescolar.model.request;

import lombok.Setter;

import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class PessoaRequest {

	private String nome;
	
	private String telefone;
	
	private String email;	
	
}
