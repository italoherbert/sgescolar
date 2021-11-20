package sgescolar.msg;

public interface ValidacaoErro {

	public final static String USERNAME_OBRIGATORIO = "O nome de usuário é um campo de preenchimento obrigatório.";
	public final static String PASSWORD_OBRIGATORIO = "A senha é um campo de preenchimento obrigatório.";
	public final static String USUARIO_PERFIL_OBRIGATORIO = "O perfil do usuário é um campo de preenchimento obrigatório.";
	public final static String USUARIO_GRUPO_NOME_OBRIGATORIO = "O nome do grupo de usuário é um campo de preenchimento obrigatório.";
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
	public final static String DADOS_ENDERECO_OBRIGATORIOS = "Os dados da entidade endereço são obrigatórios.";
	public final static String DADOS_CONTATO_INFO_OBRIGATORIOS = "Os dados da entidade contato info são obrigatórios.";
	public final static String CPF_OBRIGATORIO = "O CPF é um campo de preenchimento obrigatório.";
	public final static String RG_OBRIGATORIO = "O RG é um campo de preenchimento obrigatório.";
	public final static String CODIGO_INEP_OBRIGATORIO = "O código INEP é um campo de preenchimento obrigatório.";
	public final static String INSTITUICAO_CNPJ_OBRIGATORIO = "O CNPJ da instituição é um campo de preenchimento obrigatório.";
	public final static String INSTITUICAO_RAZAO_SOCIAL_OBRIGATORIA = "A razão social da instituição é um campo de preenchimento obrigatório.";
	
	public final static String DATA_INICIO_BIMESTRE_OBRIGATORIA = "A data de início do bimestre é um campo obrigatório.";	
	public final static String DATA_FIM_BIMESTRE_OBRIGATORIA = "A data de início do bimestre é um campo obrigatório.";	
	public final static String DATA_INICIO_LANCAMENTO_BIMESTRE_OBRIGATORIA = "A data de início para lançamentos do bimestre é um campo obrigatório.";	
	public final static String DATA_FIM_LANCAMENTO_BIMESTRE_OBRIGATORIA = "A data de início para lançamentos do bimestre é um campo obrigatório.";	
	public final static String DATA_INICIO_FERIADO_OBRIGATORIA = "A data de início do feriado é um campo obrigatório.";	
	public final static String DATA_FIM_FERIADO_OBRIGATORIA = "A data de início do feriado é um campo obrigatório.";	
	public final static String DATA_DIA_LETIVO_OBRIGATORIA = "A data do dia letivo é um campo obrigatório.";	
	public final static String ANO_ANO_LETIVO_OBRIGATORIO = "O ano do ano letivo é um campo obrigatório.";
	public final static String DESCRICAO_FERIADO_OBRIGATORIA = "A descrição do feriado é um campo de preenchimento obrigatório.";
	
	public final static String CPF_PAI_OBRIGATORIO = "Informe o CPF do pai ou indique que o pai é desconhecido.";
	public final static String CPF_MAE_OBRIGATORIO = "Informe o CPF da mãe ou indique que a mãe é desconhecida.";
	public final static String NOME_PAI_OBRIGATORIO = "Informe o nome do pai ou indique que o pai é desconhecido.";
	public final static String NOME_MAE_OBRIGATORIO = "Informe o nome da mãe ou indique que a mãe é desconhecida.";
		
	public final static String PERMISSAO_TIPO_NAO_RECONHECIDO = "Tipo de permissão inválido.";
	public final static String CURSO_MODALIDADE_NAO_RECONHECIDA = "Modalidade de curso não reconhecida pelo sistema.";
	public final static String ESTADO_CIVIL_NAO_RECONHECIDO = "Estado civil não reconhecido pelo sistema.";
	public final static String SEXO_NAO_RECONHECIDO = "Sexo não reconhecido pelo sistema.";
	public final static String NACIONALIDADE_NAO_RECONHECIDA = "Nacionalidade não reconhecida pelo sistema.";
	public final static String RACA_NAO_RECONHECIDA = "Raca não reconhecida pelo sistema.";
	public final static String RELIGIAO_NAO_RECONHECIDA = "Religião não reconhecida pelo sistema.";
	public final static String USUARIO_PERFIL_NAO_RECONHECIDO = "Perfil de usuário não reconhecido pelo sistema. Perfil=$1";
	public final static String ESCOLARIDADE_NAO_RECONHECIDA = "Escolaridade não reconhecida pelo sistema.";
	public final static String FUNCIONARIO_FUNCAO_NAO_RECONHECIDA = "Função do funcionário não reconhecida pelo sistema.";
	
	public final static String UID_NAO_EXTRAIDO_DE_TOKEN = "ID do usuário logado não extraído do token.";
	public final static String EID_NAO_EXTRAIDO_DE_TOKEN = "ID da escola vinculada ao usuário logado não extraído do token.";
	public final static String EID_NAO_DEVERIA_SER_EXTRAIDO_DE_TOKEN = "ID de escola embutido no token, mesmo para usuário sem perfil de secretário.";
	
	public final static String UID_NULO = "ID do usuário requerido.";
	public final static String UID_INVALIDO = "O ID do usuário informado está em formato inválido.";
	public final static String UID_NAO_CORRESPONDE_AO_DO_TOKEN = "O ID do usuário informado não corresponde ao do token.";
	
	public final static String EID_NULO = "ID de escola do usuário requerido.";
	public final static String EID_INVALIDO = "O ID de escola do usuário informado está em formato inválido.";
	public final static String EID_NAO_CORRESPONDE_AO_DO_TOKEN = "O ID de escola do usuário informado não corresponde ao do token.";
	
	public final static String DATA_INI_INVALIDA = "A data de início está em formato inválido. Valor=$1";
	public final static String DATA_FIM_INVALIDA = "A data de fim está em formato inválido. Valor=$1";
	public final static String DATA_INI_APOS_DATA_FIM = "A data de início tem valor de antes da data de fim";
	public final static String PERMISSAO_LEITURA_INVALIDA = "Permissão de leitura não booleana.";
	public final static String PERMISSAO_ESCRITA_INVALIDA = "Permissão de escrita não booleana.";				
	public final static String PERMISSAO_REMOCAO_INVALIDA = "Permissão de remoção não booleana.";	
	public final static String SEM_PERMISSAO_PARA_REG_POR_PERFIL = "Você não tem permissão para registrar usuários com o perfil informado.";	
	public final static String DATA_NASCIMENTO_INVALIDA = "Data de nascimento em formato inválido.";
	public final static String EH_ESCOLA_FUNCIONARIO_VALOR_INVALIDO = "O valor do campo que indica se o funcionário é de uma escola está em formato inválido.";
	public final static String CARGA_HORARIA_INVALIDA = "Carga horária inválida.";
	public final static String USUARIO_PERFIL_NAO_CORRESPONDE_AO_ESPERADO = "O perfil do usuário logado não corresponde ao esperado para esta operação.";
	public final static String CPF_ALUNO_IGUAL_A_CPF_PAI = "O CPF informado do aluno é igual ao CPF informado do pai do aluno.";
	public final static String CPF_ALUNO_IGUAL_A_CPF_MAE = "O CPF informado do aluno é igual ao CPF informado da mãe do aluno.";
	public final static String CPF_PAI_IGUAL_A_CPF_MAE = "O CPF informado do pai do aluno é igual ao CPF informado da mãe do aluno.";
	public final static String CPF_INVALIDO = "O CPF informado: $1, não passou no teste de validação.";
	public final static String CPF_PAI_INVALIDO = "O CPF para o pai informado: $1, não passou no teste de validação.";
	public final static String CPF_MAE_INVALIDO = "O CPF para a mãe informado: $1, não passou no teste de validação.";
	public final static String SECRETARIO_PERFIL_INVALIDO = "Perfil de secretário inválido.";
	public final static String DESCONHECIDO_FORMATO_INVALIDO = "O campo que informa se o pai ou mãe é desconhecido está em formato inválido.";	
	public final static String PAI_FALECIDO_FORMATO_INVALIDO = "O campo que informa se o pai já faleceu está em formato inválido.";
	public final static String MAE_FALECIDA_FORMATO_INVALIDO = "O campo que informa se a mãe já faleceu está em formato inválido.";
	public final static String FALECIDO_FORMATO_INVALIDO = "O valor do campo falecido está em formato inválido.";
		
	public final static String DIA_LETIVO_DATA_INVALIDA = "A data do dia letivo está em formato inválido.";
	public final static String BIMESTRE_DATA_INICIO_INVALIDA = "A data de início do bimestre está em formato inválido.";
	public final static String BIMESTRE_DATA_FIM_INVALIDA = "A data de fim do bimestre está em formato inválido.";
	public final static String BIMESTRE_LANCAMENTO_DATA_INICIO_INVALIDA = "A data de início de lançamentos do bimestre está em formato inválido.";
	public final static String BIMESTRE_LANCAMENTO_DATA_FIM_INVALIDA = "A data de fim de lançamentos do bimestre está em formato inválido.";
	
	public final static String FERIADO_DATA_INICIO_INVALIDA = "A data de início do feriado está em formato inválido.";
	public final static String FERIADO_DATA_FIM_INVALIDA = "A data de fim do feriado está em formato inválido.";

	public final static String ANO_LETIVO_ANO_INVALIDA = "O ano do ano letivo está em formato não inteiro.";
		
	public final static String PRIMEIRO_BIMESTRE_NULO = "Os dados do primeiro bimestre são obrigatórios.";
	public final static String SEGUNDO_BIMESTRE_NULO = "Os dados do segundo bimestre são obrigatórios.";
	public final static String TERCEIRO_BIMESTRE_NULO = "Os dados do terceiro bimestre são obrigatórios.";
	public final static String QUARTO_BIMESTRE_NULO = "Os dados do quarto bimestre são obrigatórios.";
	
	
}
