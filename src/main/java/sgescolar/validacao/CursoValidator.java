package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.request.filtro.FiltraCursosRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class CursoValidator {

	@Autowired
	private CursoModalidadeEnumManager modalidadeEnum;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveCursoRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		
		if ( request.getCargaHoraria() == null )
			throw new ValidacaoException( ValidacaoErro.CURSO_CARGA_HORARIA_OBRIGATORIA );
		if ( request.getCargaHoraria().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CURSO_CARGA_HORARIA_OBRIGATORIA );
		
		if ( !validatorUtil.intValido( request.getCargaHoraria() ) )
			throw new ValidacaoException( ValidacaoErro.CARGA_HORARIA_INVALIDA );
		
		if ( !modalidadeEnum.enumValida( request.getModalidade() ) )
			throw new ValidacaoException( ValidacaoErro.CURSO_MODALIDADE_NAO_RECONHECIDA );
	}
	
	public void validaFiltroRequest( FiltraCursosRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );	
		
	}
	
}
