package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.PeriodoTipo;

@Component
public class PeriodoEnumManager extends AbstractEnumManager<PeriodoTipo> { 

	@Override
	public PeriodoTipo[] values() {
		return PeriodoTipo.values();
	}

	@Override
	protected String label(PeriodoTipo e) {
		return e.label();
	}
		
}
