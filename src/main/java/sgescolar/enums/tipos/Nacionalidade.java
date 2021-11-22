package sgescolar.enums.tipos;

public enum Nacionalidade {
	BRASILEIRO, ESTRANGEIRO;
	
	public String descricao() {
		switch( this ) {
			case BRASILEIRO: return "Brasileiro(a)";
			case ESTRANGEIRO: return "Estrangeiro(a)";
		}
		return null;
	}
}
