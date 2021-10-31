package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.EstadoCivil;

@Component
public class EstadoCivilEnumManager extends AbstractEnumManager<EstadoCivil> { 

	@Override
	public EstadoCivil[] values() {
		return EstadoCivil.values();
	}
		
}
	