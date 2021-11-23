package sgescolar.enums.tipos;

public enum PeriodoTipo {
	BIMESTRE, TRIMESTRE, SEMESTRE;
	
	public String texto() {
		switch( this ) {
			case BIMESTRE: return "Bimestre";
			case TRIMESTRE: return "Trimestre";
			case SEMESTRE: return "Semestre";
		}
		return null;
	}
}
