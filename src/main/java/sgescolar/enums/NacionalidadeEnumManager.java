package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.Nacionalidade;

@Component
public class NacionalidadeEnumManager extends AbstractEnumManager<Nacionalidade> { 

	@Override
	public Nacionalidade[] values() {
		return Nacionalidade.values();
	}
		
}