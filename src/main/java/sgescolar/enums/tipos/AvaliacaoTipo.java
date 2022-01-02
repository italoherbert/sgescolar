package sgescolar.enums.tipos;

public enum AvaliacaoTipo {
	NOTA, CONCEITUAL, DESCRITIVA;
	
	public String label() {
		switch( this ) {
			case NOTA: return "Por nota";
			case CONCEITUAL: return "Conceitual";
			case DESCRITIVA : return "Descritiva";
		}
		return null;
	}
	
}
