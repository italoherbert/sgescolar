package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.FiltraDisciplinasRequest;
import sgescolar.model.request.SaveDisciplinaRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class DisciplinaValidator {
		
	public void validaSaveRequest( SaveDisciplinaRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_DISCIPLINA_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_DISCIPLINA_OBRIGATORIO );
		
		if ( request.getSigla() == null )
			throw new ValidacaoException( ValidacaoErro.SIGLA_DISCIPLINA_OBRIGATORIA );
		if ( request.getSigla().isBlank() )
			throw new ValidacaoException( ValidacaoErro.SIGLA_DISCIPLINA_OBRIGATORIA );
	}
	
	public void validaFiltroRequest( FiltraDisciplinasRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_DISCIPLINA_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_DISCIPLINA_OBRIGATORIO );	
	}
	
}
