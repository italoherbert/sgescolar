package sgescolar.enums.tipos;

public enum UsuarioPerfil {
	ADMIN, DIRETOR, SECRETARIO, PROFESSOR, ALUNO;		
	
	public boolean isSecretarioOuDiretor() {
		return this == SECRETARIO || this == DIRETOR;
	}
	
	public boolean isSecretarioOuDiretorOuAdmin() {
		return this == SECRETARIO || this == DIRETOR || this == ADMIN;
	}
	
	public boolean isDiretor() {
		return this == DIRETOR;
	}
	
	public boolean isAdmin() {
		return this == ADMIN;
	}
	
	public boolean isProfessor() {
		return this == PROFESSOR;
	}
	
	public boolean isAluno() {
		return this == ALUNO;
	}
	
}
