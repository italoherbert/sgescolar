package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.FuncionarioFuncao;

@Component
public class FuncionarioFuncaoEnumManager extends AbstractEnumManager<FuncionarioFuncao> { 

	@Override
	public FuncionarioFuncao[] values() {
		return FuncionarioFuncao.values();
	}

	@Override
	protected String label(FuncionarioFuncao e) {
		return e.label();
	}
		
}