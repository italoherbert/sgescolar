package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.PeriodoLetivoTipo;

@Component
public class PeriodoLetivoEnumManager extends AbstractEnumManager<PeriodoLetivoTipo> { 

	@Override
	public PeriodoLetivoTipo[] values() {
		return PeriodoLetivoTipo.values();
	}
		
}
