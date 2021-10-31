package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.Sexo;

@Component
public class SexoEnumManager extends AbstractEnumManager<Sexo> { 
	
	@Override
	public Sexo[] values() {
		return Sexo.values();
	}
		
}