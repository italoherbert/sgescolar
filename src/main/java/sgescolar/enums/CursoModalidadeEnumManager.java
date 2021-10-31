package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.CursoModalidade;

@Component
public class CursoModalidadeEnumManager extends AbstractEnumManager<CursoModalidade> { 

	@Override
	public CursoModalidade[] values() {
		return CursoModalidade.values();
	}
		
}
