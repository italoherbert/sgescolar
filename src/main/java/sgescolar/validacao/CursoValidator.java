package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.CursoModalidadeEnumManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveCursoRequest;
import sgescolar.model.request.filtro.FiltraCursosRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class CursoValidator {

	@Autowired
	private CursoModalidadeEnumManager cursoModalidadeEnumManager;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaSaveRequest( SaveCursoRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		
		if ( request.getCargaHoraria() == null )
			throw new ValidacaoException( ValidacaoErro.CURSO_CARGA_HORARIA_OBRIGATORIA );
		if ( request.getCargaHoraria().isBlank() )
			throw new ValidacaoException( ValidacaoErro.CURSO_CARGA_HORARIA_OBRIGATORIA );
		
		if ( request.getQuantidadeAulasDia() == null )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_OBRIGATORIA );
		if ( request.getQuantidadeAulasDia().isBlank() )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_OBRIGATORIA );
				
		if ( !validatorUtil.intValido( request.getQuantidadeAulasDia() ) )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_INVALIDA );
		
		int quant = conversorUtil.stringParaInteiro( request.getQuantidadeAulasDia() );
		if ( quant < 1 || quant > 5 )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_FORA_DA_FAIXA );
		
		if ( !validatorUtil.intValido( request.getCargaHoraria() ) )
			throw new ValidacaoException( ValidacaoErro.CARGA_HORARIA_INVALIDA );
		
		if ( !cursoModalidadeEnumManager.enumValida( request.getModalidade() ) )
			throw new ValidacaoException( ValidacaoErro.CURSO_MODALIDADE_NAO_RECONHECIDA );
		
		if ( !avaliacaoTipoEnumManager.enumValida( request.getAvaliacaoTipo() ) )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_TIPO_NAO_RECONHECIDO );
	}
	
	public void validaFiltroRequest( FiltraCursosRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_CURSO_OBRIGATORIO );	
		
	}
	
}
