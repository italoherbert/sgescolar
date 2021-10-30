package sgescolar.util.enums;

import org.springframework.stereotype.Component;

import sgescolar.exception.PermissaoNaoReconhecidoException;
import sgescolar.model.enums.TipoPermissao;

@Component
public class PermissaoEnumConversor extends AbstractEnumConversor<TipoPermissao, PermissaoNaoReconhecidoException> { 

	public PermissaoNaoReconhecidoException novaException() {
		return new PermissaoNaoReconhecidoException();
	}	

	public TipoPermissao[] values() {
		return TipoPermissao.values();
	}
		
}