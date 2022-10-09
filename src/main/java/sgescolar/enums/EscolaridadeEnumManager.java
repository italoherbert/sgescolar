package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Escolaridade;

@Component
public class EscolaridadeEnumManager extends AbstractEnumManager<Escolaridade> { 

	@Override
	protected Escolaridade[] values() {
		return Escolaridade.values();
	}

	@Override
	protected String label(Escolaridade e) {
		return e.label();
	}

}
