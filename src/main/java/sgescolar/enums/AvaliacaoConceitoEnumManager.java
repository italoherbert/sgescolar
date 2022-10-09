package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.AvaliacaoConceito;

@Component
public class AvaliacaoConceitoEnumManager extends AbstractEnumManager<AvaliacaoConceito> { 

	@Override
	public AvaliacaoConceito[] values() {
		return AvaliacaoConceito.values();
	}

	@Override
	protected String label( AvaliacaoConceito valor ) {
		return valor.label();
	}
		
}
