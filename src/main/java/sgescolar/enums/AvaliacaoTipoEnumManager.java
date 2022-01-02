package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.AvaliacaoTipo;

@Component
public class AvaliacaoTipoEnumManager extends AbstractEnumManager<AvaliacaoTipo> { 

	@Override
	public AvaliacaoTipo[] values() {
		return AvaliacaoTipo.values();
	}

	@Override
	protected String label( AvaliacaoTipo valor ) {
		return valor.label();
	}
		
}

