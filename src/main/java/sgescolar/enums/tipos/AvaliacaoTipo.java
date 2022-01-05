package sgescolar.enums.tipos;

public enum AvaliacaoTipo {
	PROVA_ESCRITA, PONTOS;
	
	public String label() {
		switch( this ) {
			case PROVA_ESCRITA: return "Prova escrita";
			case PONTOS: return "Pontos";
		}
		return null;
	}
	
}
