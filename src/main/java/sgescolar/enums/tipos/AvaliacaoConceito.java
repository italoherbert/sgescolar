package sgescolar.enums.tipos;

public enum AvaliacaoConceito {
	PROCESSO_CONSTRUIDO, PROCESSO_EM_CONSTRUCAO;
	
	public String label() {
		switch( this ) {
			case PROCESSO_CONSTRUIDO: return "PC";
			case PROCESSO_EM_CONSTRUCAO: return "PEC";
		}
		return null;
	}
	
}
