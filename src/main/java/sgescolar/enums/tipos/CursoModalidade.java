package sgescolar.enums.tipos;

public enum CursoModalidade {
	ENSINO_REGULAR, EDUCACAO_ESPECIAL, EDUCACAO_JOVENS_E_ADULTOS, EDUCACAO_PROFISSIONAL;
	
	public String label() {
		switch( this ) {
			case ENSINO_REGULAR: return "Ensino regular";
			case EDUCACAO_ESPECIAL: return "Ensino especial";
			case EDUCACAO_JOVENS_E_ADULTOS : return "Educação de jovens e adultos (EJA)";
			case EDUCACAO_PROFISSIONAL : return "Educação profissional";
		}
		return null;
	}
	
}
