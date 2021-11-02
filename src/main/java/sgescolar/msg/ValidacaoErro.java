package sgescolar.msg;

public interface ValidacaoErro {

	public final static String USERNAME_OBRIGATORIO = "O nome de usuário é um campo de preenchimento obrigatório.";
	public final static String PASSWORD_OBRIGATORIO = "A senha é um campo de preenchimento obrigatório.";
	public final static String USUARIO_PERFIL_OBRIGATORIO = "O perfil do usuário é um campo de preenchimento obrigatório.";
	public final static String NOME_RECURSO_OBRIGATORIO = "O nome do recurso é um campo de preenchimento obrigatório.";
	public final static String DATA_INI_OBRIGATORIA = "A data de início é um campo de preenchimento obrigatório.";
	public final static String DATA_FIM_OBRIGATORIA = "A data de fim é um campo de preenchimento obrigatório.";		
	public final static String NOME_PESSOA_OBRIGATORIO = "O nome é um campo de preenchimento obrigatório.";
	public final static String EMAIL_PESSOA_OBRIGATORIO = "O email é um campo de preenchimento obrigatório.";
	public final static String NOME_ESCOLA_OBRIGATORIO = "O nome da escola é um campo de preenchimento obrigatório.";
	public final static String NOME_CURSO_OBRIGATORIO = "O nome do curso é um campo de preenchimento obrigatório.";
	public final static String DADOS_PESSOA_OBRIGATORIOS = "Os dados da entidade pessoa são obrigatórios.";
	public final static String DADOS_USUARIO_OBRIGATORIOS = "Os dados da entidade usuario são obrigatórios.";
	public final static String DADOS_FUNCIONARIO_OBRIGATORIOS = "Os dados da entidade funcionário são obrigatórios.";
	public final static String DADOS_PESSOA_MAE_OBRIGATORIOS = "Os dados da entidade pessoa pai são obrigatórios.";
	public final static String DADOS_PESSOA_PAI_OBRIGATORIOS = "Os dados da entidade pessoa mãe são obrigatórios.";
	public final static String CPF_OBRIGATORIO = "O CPF é um campo de preenchimento obrigatório.";
	public final static String RG_OBRIGATORIO = "O RG é um campo de preenchimento obrigatório.";
		
	public final static String PERMISSAO_TIPO_NAO_RECONHECIDO = "Tipo de permissão inválido.";
	public final static String CURSO_MODALIDADE_NAO_RECONHECIDA = "Modalidade de curso não reconhecida pelo sistema.";
	public final static String ESTADO_CIVIL_NAO_RECONHECIDO = "Estado civil não reconhecido pelo sistema.";
	public final static String SEXO_NAO_RECONHECIDO = "Sexo não reconhecido pelo sistema.";
	public final static String NACIONALIDADE_NAO_RECONHECIDA = "Nacionalidade não reconhecida pelo sistema.";
	public final static String RACA_NAO_RECONHECIDA = "Raca não reconhecida pelo sistema.";
	public final static String RELIGIAO_NAO_RECONHECIDA = "Religião não reconhecida pelo sistema.";
	public final static String USUARIO_PERFIL_NAO_RECONHECIDO = "Perfil de usuário não reconhecido pelo sistema.";
	public final static String ESCOLARIDADE_NAO_RECONHECIDA = "Escolaridade não reconhecida pelo sistema.";
	
	public final static String UID_NAO_EXTRAIDO_DE_TOKEN = "ID do usuário logado não extraído do token.";
	public final static String EID_NAO_EXTRAIDO_DE_TOKEN = "ID da escola vinculada ao usuário logado não extraído do token.";
	public final static String EID_NAO_DEVERIA_SER_EXTRAIDO_DE_TOKEN = "ID de escola embutido no token, mesmo para usuário sem perfil de secretário.";
	
	public final static String EID_NULO = "ID de escola do usuário logado requerido.";
	public final static String EID_INVALIDO = "O ID de escola do usuário logado está em formato inválido.";
	public final static String EID_NAO_CORRESPONDE_AO_DO_TOKEN = "O ID de escola do usuário logado não corresponde ao do token.";
	
	public final static String DATA_INI_INVALIDA = "A data de início está em formato inválido. Valor=$1";
	public final static String DATA_FIM_INVALIDA = "A data de fim está em formato inválido. Valor=$1";
	public final static String DATA_INI_APOS_DATA_FIM = "A data de início tem valor de antes da data de fim";
	public final static String PERMISSAO_LEITURA_INVALIDA = "Permissão de leitura não booleana.";
	public final static String PERMISSAO_ESCRITA_INVALIDA = "Permissão de escrita não booleana.";				
	public final static String PERMISSAO_REMOCAO_INVALIDA = "Permissão de remoção não booleana.";	
	public final static String DATA_NASCIMENTO_INVALIDA = "Data de nascimento em formato inválido.";
	public final static String EH_ESCOLA_FUNCIONARIO_VALOR_INVALIDO = "O valor do campo que indica se o funcionário é de uma escola está em formato inválido.";
	public final static String CARGA_HORARIA_INVALIDA = "Carga horária inválida.";
	public final static String USUARIO_PERFIL_NAO_CORRESPONDE_AO_ESPERADO = "O perfil do usuário logado não corresponde ao esperado para esta operação.";
	
}
