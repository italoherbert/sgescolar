package sgescolar.enums.tipos;

public enum Sexo {
	MASCULINO, FEMININO;
	
	public String texto() {
		switch( this ) {
			case MASCULINO: return "Masculino";
			case FEMININO: return "Feminino";
		}
		return null;
	}
}
