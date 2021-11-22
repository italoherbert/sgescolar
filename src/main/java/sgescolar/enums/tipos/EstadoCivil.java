package sgescolar.enums.tipos;

public enum EstadoCivil {
	SOLTEIRO, CASADO, DIVORCIADO, RELACAO_ESTAVEL, VIUVO;
	
	public String descricao() {
		switch( this ) {
			case SOLTEIRO: return "Solteiro(a)";
			case CASADO: return "Casado(a)";
			case DIVORCIADO : return "Divorciado(a)";
			case RELACAO_ESTAVEL : return "Relação estável";
			case VIUVO : return "Viúvo(a)";			
		}
		return null;
	}
	
}
