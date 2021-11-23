package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Religiao;

@Component
public class ReligiaoEnumManager extends AbstractEnumManager<Religiao> { 

	@Override
	public Religiao[] values() {
		return Religiao.values();
	}

	@Override
	protected String texto(Religiao e) {
		return e.texto();
	}
		
}