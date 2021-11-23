package sgescolar.enums.tipos;

public enum Religiao {
	CATOLICO, EVANGELICO, ISLAMICO, ESPIRITA, BUDISTA, ATEU;
	
	public String texto() {
		switch( this ) {
			case CATOLICO: return "Católico(a)";
			case EVANGELICO: return "Evangélico(a)";
			case ISLAMICO: return "Islâmico(a)";
			case ESPIRITA: return "Espirita";
			case BUDISTA: return "Budista";
			case ATEU: return "Ateu";
		}
		return null;
	}
}
