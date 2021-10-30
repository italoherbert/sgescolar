package sgescolar.util.enums;

import sgescolar.exception.EscolaridadeFormatoException;
import sgescolar.model.enums.Escolaridade;

public class EscolaridadeEnumConversor extends AbstractEnumConversor<Escolaridade, EscolaridadeFormatoException> {

	@Override
	protected EscolaridadeFormatoException novaException() {
		return new EscolaridadeFormatoException();
	}

	@Override
	protected Escolaridade[] values() {
		return Escolaridade.values();
	}

}
