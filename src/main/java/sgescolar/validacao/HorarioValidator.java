package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveHorarioAulaRequest;
import sgescolar.model.request.SaveHorarioRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class HorarioValidator {

	@Autowired
	private AulaValidator aulaValidator;
	
	public void validaSaveRequest( SaveHorarioRequest request ) throws ValidacaoException {
		List<SaveHorarioAulaRequest> aulas = request.getHorarioAulas();
		if ( aulas == null )
			throw new ValidacaoException( ValidacaoErro.LISTA_DE_AULAS_NULA );
		
		for( SaveHorarioAulaRequest areq : aulas )
			aulaValidator.validaSaveRequest( areq );
	}
	
}
