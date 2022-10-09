package sgescolar.enums.tipos;

public enum AvaliacaoConceito {
	PC, PEC, NAO_DISPONIVEL;
	
	public String label() {
		switch( this ) {
			case PC: return "PC";
			case PEC: return "PEC";
			case NAO_DISPONIVEL: return "Não disponível.";
		}
		return null;
	}
	
}
