package sgescolar.enums.tipos;

public enum FrequenciaModalidade {
	PRESENCIAL, SEMIPRESENCIAL, REMOTA;
	
	public String label() {
		switch( this ) {
			case PRESENCIAL: return "Presencial";
			case SEMIPRESENCIAL: return "Semi presencial";
			case REMOTA: return "Remota";
			default: return null;
		}
	}
}
