package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.EstadoCivil;

@Component
public class EstadoCivilEnumManager extends AbstractEnumManager<EstadoCivil> { 

	@Override
	public EstadoCivil[] values() {
		return EstadoCivil.values();
	}

	@Override
	protected String texto(EstadoCivil e) {
		return e.texto();
	}
		
}
	