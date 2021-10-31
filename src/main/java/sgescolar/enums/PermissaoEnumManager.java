package sgescolar.enums;

import org.springframework.stereotype.Component;

import sgescolar.model.enums.TipoPermissao;

@Component
public class PermissaoEnumManager extends AbstractEnumManager<TipoPermissao> { 

	@Override
	public TipoPermissao[] values() {
		return TipoPermissao.values();
	}
		
}