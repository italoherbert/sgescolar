package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.PlanejamentoTipo;

@Component
public class PlanejamentoTipoEnumManager extends AbstractEnumManager<PlanejamentoTipo> { 

	@Override
	public PlanejamentoTipo[] values() {
		return PlanejamentoTipo.values();
	}

	@Override
	protected String label( PlanejamentoTipo valor ) {
		return valor.label();
	}
		
}
