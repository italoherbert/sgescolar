package sgescolar.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroResponse {

	public final static int PESSOA_NAO_ENCONTRADA = 1;
	public final static int ESCOLA_NAO_ENCONTRADA = 2;
	
	public final static int PESSOA_JA_EXISTE = 100;
	public final static int ESCOLA_JA_EXISTE = 101;
	
	public final static int NOME_PESSOA_OBRIGATORIO = 200;
	public final static int EMAIL_PESSOA_OBRIGATORIO = 201;	
	public final static int NOME_ESCOLA_OBRIGATORIO = 202;
	
	private int codigo;
	private String mensagem;
	
	public ErroResponse( int cod, String... params ) {
		this.codigo = cod;
		
		switch( cod ) {
			case PESSOA_NAO_ENCONTRADA:
				mensagem = "Pessoa não encontrada.";
				break;
			case ESCOLA_NAO_ENCONTRADA:
				mensagem = "Escola não encontrada.";
				break;
				
			case PESSOA_JA_EXISTE:
				mensagem = "Já existe uma pessoa cadastrada com o nome informado.";
				break;
			case ESCOLA_JA_EXISTE:
				mensagem = "Já existe uma escola cadastrada com o nome informado.";
				break;
				
			case NOME_PESSOA_OBRIGATORIO:
				mensagem = "O nome é um campo de preenchimento obrigatório.";
				break;
			case EMAIL_PESSOA_OBRIGATORIO:
				mensagem = "O email é um campo de preenchimento obrigatório.";
				break;
			case NOME_ESCOLA_OBRIGATORIO:
				mensagem = "O nome da escola é um campo de preenchimento obrigatório.";
				break;
			default: 
				mensagem = "Erro desconhecido.";
		}
	}
	
}
