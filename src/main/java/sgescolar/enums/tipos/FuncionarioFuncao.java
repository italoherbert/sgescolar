package sgescolar.enums.tipos;

public enum FuncionarioFuncao {

	ADMIN, DIRETOR, DIRETOR_ADJUNTO, SECRETARIO, PROFESSOR, PROFESSOR_ADJUNTO;
	
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
