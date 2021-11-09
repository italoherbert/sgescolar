package sgescolar.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SavePessoaRequest {
		
	private String nome;
	
	private String nomeSocial;
	
	private String dataNascimento;
	
	private String cpf;
	
	private String rg;
	
	private String sexo;
	
	private String estadoCivil;
	
	private String nacionalidade;
	
	private String raca;
	
	private String religiao;
	
	private SaveEnderecoRequest endereco;
	
	private SaveContatoInfoRequest contatoInfo;	
	
}
