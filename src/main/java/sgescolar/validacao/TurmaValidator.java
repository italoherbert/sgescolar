package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.TurnoEnumManager;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.model.request.filtro.FiltraTurmasRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class TurmaValidator {

	@Autowired
	private TurnoEnumManager turnoEnumManager;
	
	public void validaSaveRequest( SaveTurmaRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		
		if ( !turnoEnumManager.enumValida( request.getTurno() ) )
			throw new ValidacaoException( ValidacaoErro.TURNO_NAO_RECONHECIDO );
	}
	
	public void validaFiltroRequest( FiltraTurmasRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );	
	}
	
}
