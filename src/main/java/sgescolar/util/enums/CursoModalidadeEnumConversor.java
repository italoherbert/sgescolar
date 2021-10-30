package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.CursoModalidadeNaoReconhecidaException;
import sgescolar.model.enums.CursoModalidade;

@Component
public class CursoModalidadeEnumConversor extends AbstractEnumConversor<CursoModalidade, CursoModalidadeNaoReconhecidaException> { 

	public CursoModalidadeNaoReconhecidaException novaException() {
		return new CursoModalidadeNaoReconhecidaException();
	}
	
	public CursoModalidade[] values() {
		return CursoModalidade.values();
	}
		
}
