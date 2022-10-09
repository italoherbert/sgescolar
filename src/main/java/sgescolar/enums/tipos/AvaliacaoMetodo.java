package sgescolar.enums.tipos;

public enum AvaliacaoMetodo {
	NUMERICA, CONCEITUAL, DESCRITIVA;
	
	public String label() {
		switch( this ) {
			case NUMERICA: return "Numérica";
			case CONCEITUAL: return "Conceitual";
			case DESCRITIVA : return "Descritiva";
		}
		return null;
	}
	
}
