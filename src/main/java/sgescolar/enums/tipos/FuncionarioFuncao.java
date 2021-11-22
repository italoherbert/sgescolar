package sgescolar.enums.tipos;

public enum FuncionarioFuncao {

	ADMIN, DIRETOR, DIRETOR_ADJUNTO, SECRETARIO, PROFESSOR, PROFESSOR_ADJUNTO;
	
	public String descricao() {
		switch( this ) {
			case ADMIN: return "Administrador(a)";
			case DIRETOR: return "Diretor(a)";
			case DIRETOR_ADJUNTO: return "Diretor(a) adjunto(a)";
			case SECRETARIO: return "Secretário(a)";
			case PROFESSOR: return "Professor(a)";
			case PROFESSOR_ADJUNTO: return "Professor(a) adjunto(a)";
		}
		return null;
	}
	
	public UsuarioPerfil getPerfil() {
		switch( this ) {
			case ADMIN: 
				return UsuarioPerfil.ADMIN;
			case DIRETOR:
			case DIRETOR_ADJUNTO:
			case SECRETARIO: 
				return UsuarioPerfil.SECRETARIO;
			case PROFESSOR:
			case PROFESSOR_ADJUNTO:
				return UsuarioPerfil.PROFESSOR;					
		}
		return UsuarioPerfil.ADMIN;
	}
	
}
