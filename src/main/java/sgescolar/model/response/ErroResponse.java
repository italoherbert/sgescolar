package sgescolar.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroResponse {

	public final static int SEM_PERMISSAO = 1;
	public final static int SEM_PERMISSAO_REG_USUARIO_RAIZ = 2;
	
	public final static int USUARIO_NAO_ENCONTRADO = 100;
	public final static int USUARIO_LOGADO_NAO_ENCONTRADO = 101;
	public final static int USUARIO_GRUPO_NAO_ENCONTRADO = 102;
	public final static int RECURSO_NAO_ENCONTRADO = 103;
	public final static int PERMISSAO_GRUPO_NAO_ENCONTRADO = 104;
	public final static int PESSOA_NAO_ENCONTRADA = 105;
	public final static int ESCOLA_NAO_ENCONTRADA = 106;
	
	public final static int USUARIO_JA_EXISTE = 200;
	public final static int USUARIO_GRUPO_JA_EXISTE = 201;
	public final static int RECURSO_JA_EXISTE = 202;
	public final static int PESSOA_JA_EXISTE = 203;
	public final static int ESCOLA_JA_EXISTE = 204;

	public final static int USERNAME_OBRIGATORIO = 300;
	public final static int PASSWORD_OBRIGATORIO = 301;	
	public final static int USUARIO_GRUPO_OBRIGATORIO = 302;
	public final static int DATA_INI_OBRIGATORIA = 303;
	public final static int DATA_FIM_OBRIGATORIA = 304;
	public final static int NOME_RECURSO_OBRIGATORIO = 305;
	public final static int NOME_USUARIO_GRUPO_OBRIGATORIO = 306;
	public final static int NOME_PESSOA_OBRIGATORIO = 307;
	public final static int EMAIL_PESSOA_OBRIGATORIO = 308;	
	public final static int NOME_ESCOLA_OBRIGATORIO = 309;

	public final static int USERNAME_PASSWORD_NAO_CORRESPONDEM = 500;
	public final static int TENTATIVA_DELETAR_GRUPO_NAO_VAZIO = 501;
	public final static int PERMISSAO_LEITURA_INVALIDA = 502;
	public final static int PERMISSAO_ESCRITA_INVALIDA = 503;
	public final static int PERMISSAO_REMOCAO_INVALIDA = 504;
	public final static int PERMISSAO_TIPO_INVALIDO = 505;
	public final static int DATA_INI_INVALIDA = 506;
	public final static int DATA_FIM_INVALIDA = 507;
	public final static int DATA_INI_APOS_DATA_FIM = 508;
	
	private int codigo;
	private String mensagem;
	
	public ErroResponse( int cod, String... params ) {
		this.codigo = cod;
		
		switch( cod ) {
			case SEM_PERMISSAO:
				mensagem = "Você não tem permissão para acessar o recurso solicitado.";
				break;
			case SEM_PERMISSAO_REG_USUARIO_RAIZ:
				mensagem = "Você não tem permissão para registrar usuário tipo RAIZ.";
				break;	
		
			case USUARIO_NAO_ENCONTRADO:
				mensagem = "Usuário não encontrado.";
				break;
			case RECURSO_NAO_ENCONTRADO:
				mensagem = "Recurso não encontrado.";
				break;
			case USUARIO_GRUPO_NAO_ENCONTRADO:
				mensagem = "Grupo de usuário não encontrado.";
				break;
			case PERMISSAO_GRUPO_NAO_ENCONTRADO:
				mensagem = "Grupo de permissões não encontrado.";
				break;
			case USUARIO_LOGADO_NAO_ENCONTRADO:
				mensagem = "Usuário logado não encontrado.";
				break;
			case PESSOA_NAO_ENCONTRADA:
				mensagem = "Pessoa não encontrada.";
				break;
			case ESCOLA_NAO_ENCONTRADA:
				mensagem = "Escola não encontrada.";
				break;
				
			case USUARIO_JA_EXISTE:
				mensagem = "Já existe outro usuário para o username informado.";
				break;
			case USUARIO_GRUPO_JA_EXISTE:
				mensagem = "Já existe um grupo de usuario cadastrado com o nome informado.";
				break;
			case RECURSO_JA_EXISTE:
				mensagem = "Já existe um recurso registrado com o nome informado.";
				break;
			case PESSOA_JA_EXISTE:
				mensagem = "Já existe uma pessoa cadastrada com o nome informado.";
				break;
			case ESCOLA_JA_EXISTE:
				mensagem = "Já existe uma escola cadastrada com o nome informado.";
				break;
				
			case USERNAME_OBRIGATORIO:
				mensagem = "O nome de usuário é um campo de preenchimento obrigatório.";
				break;
			case PASSWORD_OBRIGATORIO:
				mensagem = "A senha é um campo de preenchimento obrigatório.";
				break;
			case USUARIO_GRUPO_OBRIGATORIO:
				mensagem = "O grupo do usuário é um campo de preenchimento obrigatório.";
				break;
			case NOME_USUARIO_GRUPO_OBRIGATORIO:
				mensagem = "O nome do grupo de usuário é um campo de preenchimento obrigatório.";
				break;
			case NOME_RECURSO_OBRIGATORIO:
				mensagem = "O nome do recurso é um campo de preenchimento obrigatório.";
				break;
			case DATA_INI_OBRIGATORIA:
				mensagem = "A data de início é um campo de preenchimento obrigatório.";
				break;
			case DATA_FIM_OBRIGATORIA:
				mensagem = "A data de fim é um campo de preenchimento obrigatório.";
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
				
			case USERNAME_PASSWORD_NAO_CORRESPONDEM:
				mensagem = "Nome de usuário e senha não correspondem.";
				break;
			case DATA_INI_INVALIDA:
				mensagem = "A data de início está em formato inválido. Valor="+params[0];
				break;
			case DATA_FIM_INVALIDA:
				mensagem = "A data de fim está em formato inválido. Valor="+params[0];
				break;
			case DATA_INI_APOS_DATA_FIM:
				mensagem = "A data de início tem valor de antes da data de fim";
				break;
			case TENTATIVA_DELETAR_GRUPO_NAO_VAZIO:
				mensagem = "Tentativa de deletar grupo que contém usuários";
				break;
			case PERMISSAO_LEITURA_INVALIDA:
				mensagem = "Permissão de leitura não booleana.";
				break;
			case PERMISSAO_ESCRITA_INVALIDA:
				mensagem = "Permissão de escrita não booleana.";				
				break;
			case PERMISSAO_REMOCAO_INVALIDA:
				mensagem = "Permissão de remoção não booleana.";	
				break;
			case PERMISSAO_TIPO_INVALIDO:
				mensagem = "Tipo de permissão inválido.";
				break;
			default: 
				mensagem = "Erro desconhecido.";
		}
	}
	
}
