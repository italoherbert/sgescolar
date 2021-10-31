package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.Escolaridade;

@Component
public class EscolaridadeEnumManager extends AbstractEnumManager<Escolaridade> { 

	@Override
	protected Escolaridade[] values() {
		return Escolaridade.values();
	}

}
