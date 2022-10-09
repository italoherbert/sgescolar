package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.FrequenciaModalidade;

@Component
public class FrequenciaModalidadeEnumManager extends AbstractEnumManager<FrequenciaModalidade> { 

	@Override
	public FrequenciaModalidade[] values() {
		return FrequenciaModalidade.values();
	}

	@Override
	protected String label( FrequenciaModalidade valor ) {
		return valor.label();
	}
		
}

