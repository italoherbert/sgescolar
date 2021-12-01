package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Raca;

@Component
public class RacaEnumManager extends AbstractEnumManager<Raca> { 

	@Override
	public Raca[] values() {
		return Raca.values();
	}

	@Override
	protected String label(Raca e) {
		return e.label();
	}
		
}