package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.NacionalidadeNaoReconhecidoException;
import sgescolar.model.enums.Nacionalidade;

@Component
public class NacionalidadeEnumConversor extends AbstractEnumConversor<Nacionalidade, NacionalidadeNaoReconhecidoException> { 

	public NacionalidadeNaoReconhecidoException novaException() {
		return new NacionalidadeNaoReconhecidoException();
	}
	
	public Nacionalidade[] values() {
		return Nacionalidade.values();
	}
		
}