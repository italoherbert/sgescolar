package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.SexoNaoReconhecidoException;
import sgescolar.model.enums.Sexo;

@Component
public class SexoEnumConversor extends AbstractEnumConversor<Sexo, SexoNaoReconhecidoException> { 
	
	@Override
	protected SexoNaoReconhecidoException novaException() {
		return new SexoNaoReconhecidoException();
	} 

	public Sexo[] values() {
		return Sexo.values();
	}
		
}