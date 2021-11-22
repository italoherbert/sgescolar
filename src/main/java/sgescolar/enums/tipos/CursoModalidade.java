package sgescolar.enums.tipos;

public enum CursoModalidade {
	PRESENCIAL, SEMIPRESENCIAL, REMOTO;
	
	public String descricao() {
		switch( this ) {
			case PRESENCIAL: return "Presencial";
			case SEMIPRESENCIAL: return "Semi presencial";
			case REMOTO : return "Remoto";
		}
		return null;
	}
	
}
