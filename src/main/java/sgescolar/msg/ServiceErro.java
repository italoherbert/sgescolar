package sgescolar.msg;

public interface ServiceErro {

	public final static String SEM_PERMISSAO = "Você não tem permissão para acessar o recurso solicitado.";
	public final static String SEM_PERMISSAO_REG_USUARIO_RAIZ = "Você não tem permissão para registrar usuário tipo RAIZ.";
	public final static String SEM_PERMISSAO_POR_ESCOPO_ESCOLA = "Recurso restrito a outra escola";
	public final static String SEM_PERMISSAO_POR_ESCOPO_INSTITUICAO = "Recurso restrito a outra instituição";
		
	public final static String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";
	public final static String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado.";
	public final static String USUARIO_GRUPO_NAO_ENCONTRADO = "Grupo de usuário não encontrado.";
	public final static String PERMISSAO_GRUPO_NAO_ENCONTRADO = "Grupo de permissões não encontrado.";
	public final static String USUARIO_LOGADO_NAO_ENCONTRADO = "Usuário logado não encontrado.";
	public final static String PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada.";
	public final static String ESCOLA_NAO_ENCONTRADA = "Escola não encontrada.";
	public final static String CURSO_NAO_ENCONTRADO = "Curso não encontrado.";
	public final static String ALUNO_NAO_ENCONTRADO = "Aluno não encontrado.";
	public final static String PROFESSOR_NAO_ENCONTRADO = "Professor não encontrado.";	
	public final static String SECRETARIO_NAO_ENCONTRADO = "Secretário não encontrado.";
	public final static String ADMINISTRADOR_NAO_ENCONTRADO = "Administrador não encontrado.";
	public final static String PERFIL_NAO_VINCULADO_A_USUARIO_GRUPO = "Não foi encontrado nenhum grupo de usuário para o perfil: $1";
	public final static String RECURSO_NOME_NAO_ENCONTRADO = "Recurso não encontrado pelo nome. Nome=$1";
	public final static String INSTITUICAO_NAO_ENCONTRADA = "Instituição não encontrada.";
	public final static String ANO_LETIVO_NAO_ENCONTRADO = "Ano letivo não encontrado.";
	public final static String PERIODO_NAO_ENCONTRADO = "Período não encontrado.";
	public final static String FERIADO_NAO_ENCONTRADO = "Feriado não encontrado.";
	public final static String SERIE_NAO_ENCONTRADA = "Serie não encontrada.";
	public final static String DISCIPLINA_NAO_ENCONTRADA = "Disciplina não encontrada.";
	public final static String TURMA_NAO_ENCONTRADA = "Turma não encontrada.";
	public final static String TURMA_DISCIPLINA_NAO_ENCONTRADA = "Não foi encontrado nenhum vinculo entre turma e disciplina pelas informações fornecidas.";
	public final static String PROFESSOR_ALOCACAO_NAO_ENCONTRADA = "Não foi encontrada a alocação de professores a disciplina e turma informadas.";
	public final static String MATRICULA_NAO_ENCONTRADA = "Matrícula não encontrada.";
	public final static String LISTA_ALUNO_FREQUENCIA_NAO_ENCONTRADA = "Lista de frequências de alunos não encontrada.";
	public final static String AULA_NAO_ENCONTRADA = "Aula não encontrada.";
	
	public final static String USUARIO_JA_EXISTE = "Já existe outro usuário para o username informado.";
	public final static String USUARIO_GRUPO_JA_EXISTE = "Já existe um grupo de usuario cadastrado com o nome informado.";
	public final static String RECURSO_JA_EXISTE = "Já existe um recurso registrado com o nome informado.";
	public final static String PESSOA_JA_EXISTE = "Já existe uma pessoa cadastrada com o CPF informado.";
	public final static String ESCOLA_JA_EXISTE = "Já existe uma escola cadastrada com o nome informado.";
	public final static String CURSO_JA_EXISTE = "Já existe um curso cadastrado com o nome informado.";
	public final static String PESSOA_PAI_JA_EXISTE = "Já existe uma pessoa cadastrada com o CPF informado para o pai.";
	public final static String PESSOA_MAE_JA_EXISTE = "Já existe uma pessoa cadastrada com o CPF informado para a mãe.";				
	public final static String INSTITUICAO_JA_EXISTE = "Já existe uma instituição cadastrada com o CNPJ informado.";				
	public final static String ANO_LETIVO_JA_EXISTE = "Já existe ano letivo registrado para o ano: $1";
	public final static String DISCIPLINA_JA_EXISTE = "Já existe uma disciplina registrada com a descrição informada.";
	public final static String SERIE_JA_EXISTE = "Já existe uma série registrada com o nome informado.";
	public final static String TURMA_JA_EXISTE = "Já existe uma turma registrada com a descrição informada.";
	public final static String TURMA_DISCIPLINA_JA_EXISTE = "Vínculo entre a turma e disciplina informadas já existe."; 
	public final static String PROFESSOR_ALOCACAO_JA_EXISTE = "Vínculo entre o professor e a turma e a disciplina informadas já existe."; 
	public final static String MATRICULA_JA_EXISTE = "O aluno informado já havia sido matriculado antes.";

	public final static String PERFIL_NAO_ALTERAVEL = "Para manter a consistência da base de dados, a alteração de perfil está desativada.";
	public final static String GRUPO_DE_PERFIL_NAO_DELETAVEL = "O grupo não pôde ser removido porque o nome dele está vinculado a um perfil de usuário.";
	public final static String NOME_DE_GRUPO_DE_PERFIL_NAO_ALTERAVEL = "O nome do grupo não pôde ser alterado porque está vinculado a um perfil.";
	
	public final static String VINCULO_TURMA_DISCIPLINA_RELACIONADO_NAO_DELETADO = 
			"Não foi possível deletar o vínculo turma e disciplina.\n"
			+ "porque o mesmo está relacionado a professores.\n"
			+ "Delete os vínculos com professores primeiro";

	public final static String USERNAME_PASSWORD_NAO_CORRESPONDEM = "Nome de usuário e senha não correspondem.";
	public final static String NAO_EH_DONO = "É necessário estar logado com os dados do usuário dono deste recurso para utilizá-lo.";
	
	public final static String PROFESSOR_SEM_VINCULO_COM_DISCIPLINAS = "Você está logado como professor, mas, não está vinculado a nenhuma disciplina. Solicite um vinculo para poder utilizar o sistema.";
	
}
