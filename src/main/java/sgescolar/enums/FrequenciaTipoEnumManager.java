package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.FrequenciaTipo;

@Component
public class FrequenciaTipoEnumManager extends AbstractEnumManager<FrequenciaTipo> { 

	@Override
	public FrequenciaTipo[] values() {
		return FrequenciaTipo.values();
	}

	@Override
	protected String label( FrequenciaTipo valor ) {
		return valor.label();
	}
		
}

