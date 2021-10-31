package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PermissaoEnumManager;
import sgescolar.model.enums.TipoPermissao;
import sgescolar.model.request.SavePermissaoGrupoRequest;
import sgescolar.model.request.SavePermissaoRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class PermissaoGrupoValidator {

	@Autowired
	private PermissaoEnumManager permissaoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;

	public void validaSaveRequestPermissao( SavePermissaoRequest request ) throws ValidacaoException {
		if ( !permissaoEnumManager.enumValida( request.getTipo() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_TIPO_NAO_RECONHECIDO );
		
		if ( !validatorUtil.booleanValido( request.getValor() ) ) {			
			TipoPermissao tipoPerm = permissaoEnumManager.getEnum( request.getTipo() );				
			switch( tipoPerm ) {
				case LEITURA: throw new ValidacaoException( ValidacaoErro.PERMISSAO_LEITURA_INVALIDA );
				case ESCRITA: throw new ValidacaoException( ValidacaoErro.PERMISSAO_ESCRITA_INVALIDA );				
				case REMOCAO: throw new ValidacaoException( ValidacaoErro.PERMISSAO_REMOCAO_INVALIDA );				
			}
		}
	}
	
	public void validaSaveRequestPermissaoGrupo( SavePermissaoGrupoRequest request ) throws ValidacaoException {
		if ( !validatorUtil.booleanValido( request.getLeitura() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_LEITURA_INVALIDA );
		if ( !validatorUtil.booleanValido( request.getEscrita() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_ESCRITA_INVALIDA );
		if ( !validatorUtil.booleanValido( request.getRemocao() ) )
			throw new ValidacaoException( ValidacaoErro.PERMISSAO_REMOCAO_INVALIDA );							
	}
	
}
