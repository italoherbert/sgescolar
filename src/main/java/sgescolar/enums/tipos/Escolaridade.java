package sgescolar.enums.tipos;

public enum Escolaridade {
	PRIMARIO, FUNDAMENTAL, MEDIO, GRADUACAO, POSGRADUACAO, MESTRADO, DOUTORADO, POSDOUTORADO;
	
	public String label() {
		switch( this ) {
			case PRIMARIO: return "Primário";
			case FUNDAMENTAL: return "Fundamental";
			case MEDIO : return "Médio";
			case GRADUACAO : return "Graduação";
			case POSGRADUACAO : return "Pós graduação";
			case MESTRADO : return "Mestrado";
			case DOUTORADO : return "Doutorado";
			case POSDOUTORADO : return "Pós doutorado";
		}
		return null;
	}
	
}
