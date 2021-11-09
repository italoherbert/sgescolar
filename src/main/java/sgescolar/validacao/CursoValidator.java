package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.model.request.FiltraCursosRequest;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class CursoValidator {

	@Autowired
	private CursoModalidadeEnumManager modalidadeEnum;
	
	public void validaSaveRequest( SaveCursoRequest request ) throws ValidacaoException {
		if ( !modalidadeEnum.enumValida( request.getModalidade() ) )
			throw new ValidacaoException( ValidacaoErro.CURSO_MODALIDADE_NAO_RECONHECIDA );
	}
	
	public void validaFiltroRequest( FiltraCursosRequest request ) throws ValidacaoException {
		if ( request.getNomeIni() == null )
			throw new ValidacaoException( ValidacaoErro.NOME_CURSO_OBRIGATORIO );
		if ( request.getNomeIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.NOME_CURSO_OBRIGATORIO );	
	}
	
}
