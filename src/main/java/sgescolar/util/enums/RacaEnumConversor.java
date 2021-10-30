package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.RacaNaoReconhecidoException;
import sgescolar.model.enums.Raca;

@Component
public class RacaEnumConversor extends AbstractEnumConversor<Raca, RacaNaoReconhecidoException> { 
	
	@Override
	protected RacaNaoReconhecidoException novaException() {
		return new RacaNaoReconhecidoException();
	}
	
	@Override
	public Raca[] values() {
		return Raca.values();
	}
		
}