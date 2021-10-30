package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.EstadoCivilNaoReconhecidoException;
import sgescolar.model.enums.EstadoCivil;

@Component
public class EstadoCivilEnumConversor extends AbstractEnumConversor<EstadoCivil, EstadoCivilNaoReconhecidoException> { 

	public EstadoCivilNaoReconhecidoException novaException() {
		return new EstadoCivilNaoReconhecidoException();
	}
	
	public EstadoCivil[] values() {
		return EstadoCivil.values();
	}
		
}
	