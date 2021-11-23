package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraTurmasRequest;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class TurmaValidator {

	public void validaSaveRequest( SaveTurmaRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );				
	}
	
	public void validaFiltroRequest( FiltraTurmasRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );	
	}
	
}
