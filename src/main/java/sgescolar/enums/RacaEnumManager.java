package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.Raca;

@Component
public class RacaEnumManager extends AbstractEnumManager<Raca> { 

	@Override
	public Raca[] values() {
		return Raca.values();
	}
		
}