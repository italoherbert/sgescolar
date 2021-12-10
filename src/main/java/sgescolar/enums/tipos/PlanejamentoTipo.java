package sgescolar.enums.tipos;

public enum PlanejamentoTipo {
	ENSINO, AULA;
	
	public String label() {
		switch( this ) {
			case ENSINO: return "Ensino";
			case AULA: return "Aula";
			default: return null;
		}
	}
}
