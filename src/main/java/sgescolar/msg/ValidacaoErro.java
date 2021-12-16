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
	public final static String DESCRICAO_CURSO_OBRIGATORIO = "A descrição do curso é um campo de preenchimento obrigatório.";
	public final static String DADOS_PESSOA_OBRIGATORIOS = "Os dados da entidade pessoa são obrigatórios.";
	public final static String DADOS_USUARIO_OBRIGATORIOS = "Os dados da entidade usuario são obrigatórios.";
	public final static String DADOS_FUNCIONARIO_OBRIGATORIOS = "Os dados da entidade funcionário são obrigatórios.";
	public final static String DADOS_PESSOA_MAE_OBRIGATORIOS = "Os dados da entidade pessoa pai são obrigatórios.";
	public final static String DADOS_PESSOA_PAI_OBRIGATORIOS = "Os dados da entidade pessoa mãe são obrigatórios.";
	public final static String DADOS_PESSOA_RESPONSAVEL_OBRIGATORIOS = "Os dados da entidade pessoa responsável são obrigatórios.";
	public final static String DADOS_ENDERECO_OBRIGATORIOS = "Os dados da entidade endereço são obrigatórios.";
	public final static String DADOS_CONTATO_INFO_OBRIGATORIOS = "Os dados da entidade contato info são obrigatórios.";
	public final static String CPF_OBRIGATORIO = "O CPF é um campo de preenchimento obrigatório.";
	public final static String RG_OBRIGATORIO = "O RG é um campo de preenchimento obrigatório.";
	public final static String CODIGO_INEP_OBRIGATORIO = "O código INEP é um campo de preenchimento obrigatório.";
	public final static String INSTITUICAO_CNPJ_OBRIGATORIO = "O CNPJ da instituição é um campo de preenchimento obrigatório.";
	public final static String INSTITUICAO_RAZAO_SOCIAL_OBRIGATORIA = "A razão social da instituição é um campo de preenchimento obrigatório.";
	public final static String CURSO_CARGA_HORARIA_OBRIGATORIA = "A carga horária do curso é um campo de preenchimento obrigatório.";
	public final static String AVALIACAO_PESO_OBRIGATORIO = "O peso da avaliação é um campo de preenchimento obrigatório.";
	public final static String AVALIACAO_DATA_AGENDAMENTO_OBRIGATORIO = "A data de agendamento é um campo de preenchimento obrigatório.";
	public final static String MATRICULA_OBRIGATORIA = "A matrícula é um campo de preenchimento obrigatório.";
	public final static String NOTA_OBRIGATORIA = "A nota é um campo de preenchimento obrigatório.";
	public final static String BNCC_HABILIDADE_CODIGO_OBRIGATORIO = "O código da habilidade da BNCC é um campo de preenchimento obrigatório.";
	public final static String BNCC_HABILIDADE_OBRIGATORIA = "A habilidade da BNCC é um campo de preenchimento obrigatório.";
	
	public final static String DATA_INICIO_PERIODO_OBRIGATORIA = "A data de início do período letivo é um campo obrigatório.";	
	public final static String DATA_FIM_PERIODO_OBRIGATORIA = "A data de início do período letivo é um campo obrigatório.";	
	public final static String DATA_INICIO_LANCAMENTO_PERIODO_OBRIGATORIA = "A data de início para lançamentos do período letivo é um campo obrigatório.";	
	public final static String DATA_FIM_LANCAMENTO_PERIODO_OBRIGATORIA = "A data de início para lançamentos do período letivo é um campo obrigatório.";	
	public final static String DATA_INICIO_FERIADO_OBRIGATORIA = "A data de início do feriado é um campo obrigatório.";	
	public final static String DATA_FIM_FERIADO_OBRIGATORIA = "A data de início do feriado é um campo obrigatório.";	
	public final static String DATA_DIA_LETIVO_OBRIGATORIA = "A data do dia letivo é um campo obrigatório.";	
	public final static String ANO_ANO_LETIVO_OBRIGATORIO = "O ano do ano letivo é um campo obrigatório.";
	public final static String DESCRICAO_FERIADO_OBRIGATORIA = "A descrição do feriado é um campo de preenchimento obrigatório.";
	public final static String DESCRICAO_SERIE_OBRIGATORIA = "A descrição da série é um campo de preenchimento obrigatório.";
	public final static String GRAU_SERIE_OBRIGATORIO = "O grau da série é um campo de preenchimento obrigatório.";
	public final static String DESCRICAO_DISCIPLINA_OBRIGATORIO = "A descrição da disciplina é um campo de preenchimento obrigatório.";
	public final static String DESCRICAO_TURMA_OBRIGATORIO = "A descrição da turma é um campo de preenchimento obrigatório.";
	public final static String SIGLA_DISCIPLINA_OBRIGATORIA = "A sigla correspondente a disciplina é um campo de preeenchimento obrigatório.";
	public final static String AULA_SEMANA_DIA_OBRIGATORIA = "O número do dia da aula é um campo de preenchimento obrigatório.";
	public final static String AULA_NUMERO_OBRIGATORIO = "O número da aula é um campo de preenchimento obrigatório.";
	public final static String AULA_TURNO_OBRIGATORIO = "O turno da aula é um campo de preenchimento obrigatório.";
	public final static String QUANTIDADE_AULAS_DIA_OBRIGATORIA = "A quantidade de aulas por dia é um campo de preenchimento obrigatório.";
	
	public final static String LISTA_DE_AULAS_NULA = "A lista de aulas não pode ser um valor nulo.";
	public final static String GRUPO_LISTAS_NULA = "O grupo de listas de aulas por ano letivo não pode ser um valor nulo.";
	public final static String PLANEJAMENTO_OBJETIVOS_LISTA_NULA = "A lista de objetivos não pode ser um valor nulo.";
	public final static String PLANEJAMENTO_CONTEUDOS_LISTA_NULA = "A lista de conteúdos não pode ser um valor nulo.";
	public final static String PLANEJAMENTO_ANEXOS_LISTA_NULA = "A lista de anexos não pode ser um valor nulo.";
	
	public final static String CPF_PAI_OBRIGATORIO = "Informe o CPF do pai ou indique que o pai é desconhecido.";
	public final static String CPF_MAE_OBRIGATORIO = "Informe o CPF da mãe ou indique que a mãe é desconhecida.";
	public final static String CPF_RESPONSAVEL_OBRIGATORIO = "Informe o CPF do responsável ou indique que não há necessidade de registrar responsável.";
	public final static String NOME_PAI_OBRIGATORIO = "Informe o nome do pai ou indique que o pai é desconhecido.";
	public final static String NOME_MAE_OBRIGATORIO = "Informe o nome da mãe ou indique que a mãe é desconhecida.";
	public final static String NOME_RESPONSAVEL_OBRIGATORIO = "Informe o nome do responsável ou indique que não há necessidade de registrar responsável.";
	
	public final static String PLANEJAMENTO_CONTEUDO_OBRIGATORIO = "Verifique se digitou algum conteúdo em branco.";
	public final static String PLANEJAMENTO_OBJETIVO_OBRIGATORIO = "Verifique se digitou algum objetivo em branco.";
	public final static String PLANEJAMENTO_DESCRICAO_OBRIGATORIA = "A descrição do planejamento é um campo de preenchimento obrigatório.";
	public final static String PLANEJAMENTO_DATA_INICIO_OBRIGATORIA = "A data de início do planejamento é um campo de preenchimento obrigatório.";
	public final static String PLANEJAMENTO_DATA_FIM_OBRIGATORIA = "A data de fim do planejamento é um campo de preenchimento obrigatório.";
		
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
	public final static String PERIODO_NAO_RECONHECIDO = "Tipo do período letivo não reconhecido pelo sistema.";
	public final static String TURNO_NAO_RECONHECIDO = "Turno não reconhecido pelo sistema.";
	public final static String FREQUENCIA_MODALIDADE_NAO_RECONHECIDA = "Modalidade de frequencia não reconhecida pelo sistema.";
	public final static String PLANEJAMENTO_TIPO_NAO_RECONHECIDO = "Tipo de planejamento não reconhecido pelo sistema.";
	
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
	public final static String FERIADO_DATA_INI_APOS_DATA_FIM = "A data de início do feriado tem valor maior que a data de fim";
	public final static String PERIODO_DATA_INI_APOS_DATA_FIM = "A data de início do período tem valor maior que a data de fim";
	public final static String PERIODO_LANCAMENTO_DATA_INI_APOS_DATA_FIM = "A data de início do período de lançamento tem valor maior que a data de fim";
	public final static String PERMISSAO_LEITURA_INVALIDA = "Permissão de leitura não booleana.";
	public final static String PERMISSAO_ESCRITA_INVALIDA = "Permissão de escrita não booleana.";				
	public final static String PERMISSAO_REMOCAO_INVALIDA = "Permissão de remoção não booleana.";	
	public final static String SEM_PERMISSAO_PARA_REG_POR_PERFIL = "Você não tem permissão para registrar usuários com o perfil informado.";	
	public final static String DATA_NASCIMENTO_INVALIDA = "Data de nascimento em formato inválido.";
	public final static String EH_ESCOLA_FUNCIONARIO_VALOR_INVALIDO = "O valor do campo que indica se o funcionário é de uma escola está em formato inválido.";
	public final static String CARGA_HORARIA_INVALIDA = "Carga horária inválida.";
	public final static String USUARIO_PERFIL_NAO_CORRESPONDE_AO_ESPERADO = "O perfil do usuário logado não corresponde ao esperado para esta operação.";
	public final static String CPFS_IGUAIS = "CPFs iguais. Verifique os cpfs do aluno, pai, mae e/ou responsável.";
	public final static String CPF_INVALIDO = "O CPF informado: $1, não passou no teste de validação.";
	public final static String CPF_PAI_INVALIDO = "O CPF para o pai informado: $1, não passou no teste de validação.";
	public final static String CPF_MAE_INVALIDO = "O CPF para a mãe informado: $1, não passou no teste de validação.";
	public final static String CPF_RESPONSAVEL_INVALIDO = "O CPF para o responsável informado: $1, não passou no teste de validação.";
	public final static String SECRETARIO_PERFIL_INVALIDO = "Perfil de secretário inválido.";
	public final static String DESCONHECIDO_FORMATO_INVALIDO = "O campo que informa se o pai ou mãe é desconhecido está em formato inválido.";	
	public final static String PAI_FALECIDO_FORMATO_INVALIDO = "O campo que informa se o pai já faleceu está em formato inválido.";
	public final static String MAE_FALECIDA_FORMATO_INVALIDO = "O campo que informa se a mãe já faleceu está em formato inválido.";
	public final static String FALECIDO_FORMATO_INVALIDO = "O valor do campo falecido está em formato inválido.";
	public final static String FLAG_REGISTRAR_PAI_INVALIDO = "O valor do flag que indica se o pai deve ser registrado está em formato inválido.";
	public final static String FLAG_REGISTRAR_MAE_INVALIDO = "O valor do flag que indica se a mãe deve ser registrada está em formato inválido.";
	public final static String FLAG_REGISTRAR_RESPONSAVEL_INVALIDO = "O valor do flag que indica se o responsável deve ser registrado está em formato inválido.";
		
	public final static String DIA_LETIVO_DATA_INVALIDA = "A data do dia letivo está em formato inválido.";
	public final static String PERIODO_DATA_INICIO_INVALIDA = "A data de início do período letivo está em formato inválido.";
	public final static String PERIODO_DATA_FIM_INVALIDA = "A data de fim do período letivo está em formato inválido.";
	public final static String PERIODO_LANCAMENTO_DATA_INICIO_INVALIDA = "A data de início de lançamentos do período letivo está em formato inválido.";
	public final static String PERIODO_LANCAMENTO_DATA_FIM_INVALIDA = "A data de fim de lançamentos do período letivo está em formato inválido.";
	
	public final static String FERIADO_DATA_INICIO_INVALIDA = "A data de início do feriado está em formato inválido.";
	public final static String FERIADO_DATA_FIM_INVALIDA = "A data de fim do feriado está em formato inválido.";

	public final static String ANO_LETIVO_ANO_INVALIDO = "O ano do ano letivo está em formato não inteiro.";	
	public final static String GRAU_SERIE_INVALIDO = "O grau da série está em formato inválido.";
	
	public final static String AULA_SEMANA_DIA_INVALIDO = "O número correspondente ao dia de aula está em formato inválido";
	public final static String AULA_DIA_FORA_DA_FAIXA = "O número correspondente ao dia de aula está fora da faixa permitida.";
	
	public final static String AULA_NUMERO_INVALIDO = "O número correspondente a aula do dia está em formato inválido.";
	public final static String AULA_NUMERO_FORA_DA_FAIXA = "O número correspondente a aula do dia está fora da faixa permitida.";
	
	public final static String ALUNO_ESTEVE_PRESENTE_VALOR_INVALIDO = "O valor do campo correspondente a se o aluno esteve presente está em formato inválido.";
	public final static String LISTA_ALUNO_FREQUENCIAS_NULA = "A lista de frequência fornecida tem valor nulo.";
	
	public final static String AVALIACAO_PESO_INVALIDO = "O peso informado da avaliação está em formato inválido.";
	public final static String AVALIACAO_DATA_AGENDAMENTO_INVALIDA = "A data de agendamento da avaliação está em formato inválido.";
	public final static String NOTAS_LISTA_NULA = "A lista de notas não pode ser nula.";
	public final static String NOTAS_LISTA_INCONSISTENTE = "Detectada inconsistência entre a lista de notas e a lista de matriculados na turma.";
	public final static String NOTA_INVALIDA = "Nota inválida.";
	
	public final static String PLANEJAMENTO_DATA_INICIO_INVALIDA = "A data de início do planejamento está em formato inválido.";
	public final static String PLANEJAMENTO_DATA_FIM_INVALIDA = "A data de fim do planejamento está em formato inválido.";
	
	public final static String QUANTIDADE_AULAS_DIA_INVALIDA = "A quantidade de dias está em formato inválido.";
	public final static String QUANTIDADE_AULAS_DIA_FORA_DA_FAIXA = "A quantidade de dias está fora da faixa permitida.";
	
	
}
