package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Sexo;

@Component
public class SexoEnumManager extends AbstractEnumManager<Sexo> { 
	
	@Override
	public Sexo[] values() {
		return Sexo.values();
	}

	@Override
	protected String label(Sexo e) {
		return e.texto();
	}
		
}