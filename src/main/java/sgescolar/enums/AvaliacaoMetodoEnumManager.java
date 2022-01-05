package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.AvaliacaoMetodo;

@Component
public class AvaliacaoMetodoEnumManager extends AbstractEnumManager<AvaliacaoMetodo> { 

	@Override
	public AvaliacaoMetodo[] values() {
		return AvaliacaoMetodo.values();
	}

	@Override
	protected String label( AvaliacaoMetodo valor ) {
		return valor.label();
	}
		
}

