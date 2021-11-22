package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.CursoModalidade;

@Component
public class CursoModalidadeEnumManager extends AbstractEnumManager<CursoModalidade> { 

	@Override
	public CursoModalidade[] values() {
		return CursoModalidade.values();
	}

	@Override
	protected String descricao( CursoModalidade valor ) {
		return valor.descricao();
	}
		
}
