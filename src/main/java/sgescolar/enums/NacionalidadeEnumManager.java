package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.enums.tipos.Nacionalidade;

@Component
public class NacionalidadeEnumManager extends AbstractEnumManager<Nacionalidade> { 

	@Override
	public Nacionalidade[] values() {
		return Nacionalidade.values();
	}

	@Override
	protected String descricao(Nacionalidade e) {
		return e.descricao();
	}
		
}