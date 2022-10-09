package sgescolar.enums.tipos;

public enum SemanaDia {
	DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO;
	
	public String label() {
		switch( this ) {
			case DOMINGO: return "Domingo";
			case SEGUNDA: return "Segunda";
			case TERCA: return "Terça";
			case QUARTA: return "Quarta";
			case QUINTA: return "Quinta";
			case SEXTA: return "Sexta";
			case SABADO: return "Sábado";
			default: return null;
		}
	}
	
	public int id() {
		switch( this ) {
			case DOMINGO: return 0;
			case SEGUNDA: return 1;
			case TERCA: return 2;
			case QUARTA: return 3;
			case QUINTA: return 4;
			case SEXTA: return 5;
			case SABADO: return 6;
			default: return -1;
		}
	}	
	
}
