package sgescolar.enums.tipos;

public enum UsuarioPerfil {
	RAIZ, ADMIN, SECRETARIO, PROFESSOR, ALUNO;		
	
	public String label() {
		switch( this ) {
			case RAIZ: return "Raiz(a)";
			case ADMIN: return "Administrador(a)";
			case SECRETARIO: return "Secretário(a)";
			case PROFESSOR: return "Professor(a)";
			case ALUNO: return "Aluno(a)";
		}
		return null;
	}
	
	public int getPeso() {
		switch( this ) {
			case RAIZ: return 10;
			case ADMIN: return 8;
			case SECRETARIO: return 6;
			case PROFESSOR: return 3;
			case ALUNO: return 2;
			default: return 0;
		}
	}
	
	public boolean isGrupoDePerfil( String grupo ) {
		UsuarioPerfil[] valores = UsuarioPerfil.values();
		for( UsuarioPerfil up : valores )
			if( grupo.equalsIgnoreCase( up.name() ) )
				return true;
		return false;
	}
	
		
}
