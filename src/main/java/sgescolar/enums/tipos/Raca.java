package sgescolar.enums.tipos;

public enum Raca {
	BRANCO, NEGRO, PARDO, INDIGENA;
	
	public String descricao() {
		switch( this ) {
			case BRANCO: return "Branco(a)";
			case NEGRO: return "Negro(a)";
			case PARDO: return "Pardo(a)";
			case INDIGENA: return "Indigena";
		}
		return null;
	}
	
}
