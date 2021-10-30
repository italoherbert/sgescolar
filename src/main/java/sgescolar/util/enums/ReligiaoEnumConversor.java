package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.ReligiaoNaoReconhecidoException;
import sgescolar.model.enums.Religiao;

@Component
public class ReligiaoEnumConversor extends AbstractEnumConversor<Religiao, ReligiaoNaoReconhecidoException> { 
	
	@Override
	protected ReligiaoNaoReconhecidoException novaException() {
		return new ReligiaoNaoReconhecidoException();
	}

	public Religiao[] values() {
		return Religiao.values();
	}
		
}