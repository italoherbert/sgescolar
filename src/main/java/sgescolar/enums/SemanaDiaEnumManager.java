package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.SemanaDia;

@Component
public class SemanaDiaEnumManager extends AbstractEnumManager<SemanaDia> { 

	@Override
	public SemanaDia[] values() {
		return SemanaDia.values();
	}

	@Override
	protected String label(SemanaDia e) {
		return e.label();
	}
			
	public SemanaDia getPorID( int id ) {
		switch( id ) {
			case 0: return SemanaDia.DOMINGO;
			case 1: return SemanaDia.SEGUNDA;
			case 2: return SemanaDia.TERCA;
			case 3: return SemanaDia.QUARTA;
			case 4: return SemanaDia.QUINTA;
			case 5: return SemanaDia.SEXTA;
			case 6: return SemanaDia.SABADO;
			default: return null;
		}
	}

}
