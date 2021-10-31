package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.Religiao;

@Component
public class ReligiaoEnumManager extends AbstractEnumManager<Religiao> { 

	@Override
	public Religiao[] values() {
		return Religiao.values();
	}
		
}