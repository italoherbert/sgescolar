package sgescolar.enums.tipos;

public enum UsuarioPerfil {
	ADMIN, SECRETARIO, PROFESSOR, ALUNO;		
	
	public String texto() {
		switch( this ) {
			case ADMIN: return "Administrador(a)";
			case SECRETARIO: return "Secretário(a)";
			case PROFESSOR: return "Professor(a)";
			case ALUNO: return "Aluno(a)";
		}
		return null;
	}
	
	public boolean isGrupoDePerfil( String grupo ) {
		UsuarioPerfil[] valores = UsuarioPerfil.values();
		for( UsuarioPerfil up : valores )
			if( grupo.equalsIgnoreCase( up.name() ) )
				return true;
		return false;
	}
	
	public boolean isSecretarioOuAdmin() {
		return this == SECRETARIO || this == ADMIN;
	}
	
	public boolean isSecretario() {
		return this == SECRETARIO;
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
	
	public String getUsuarioGrupo() {
		return super.name();
	}
	
}
