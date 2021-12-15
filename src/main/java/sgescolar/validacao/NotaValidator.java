package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveNotaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class NotaValidator {
	
	@Autowired
	private ValidatorUtil validatorUtil;

	public void validaSaveRequest( SaveNotaRequest request ) throws ValidacaoException {
		if ( request.getMatriculaId() == null )
			throw new ValidacaoException( ValidacaoErro.MATRICULA_OBRIGATORIA );
		
		if ( request.getNota() == null )
			throw new ValidacaoException( ValidacaoErro.NOTA_OBRIGATORIA );
		if ( request.getNota().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOTA_OBRIGATORIA );
		
		if ( !validatorUtil.doubleValido( request.getNota() ) )
			throw new ValidacaoException( ValidacaoErro.NOTA_INVALIDA );
	}
	
}
