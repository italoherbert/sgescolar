package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.TurnoEnumManager;
import sgescolar.model.request.SaveTurmaRequest;
import sgescolar.model.request.filtro.FiltraTurmasRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class TurmaValidator {

	@Autowired
	private TurnoEnumManager turnoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaSaveRequest( SaveTurmaRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		
		if ( request.getQuantidadeAulasDia() == null )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_OBRIGATORIA );
		if ( request.getQuantidadeAulasDia().isBlank() )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_OBRIGATORIA );
		
		if ( !turnoEnumManager.enumValida( request.getTurno() ) )
			throw new ValidacaoException( ValidacaoErro.TURNO_NAO_RECONHECIDO );
		
		if ( !validatorUtil.intValido( request.getQuantidadeAulasDia() ) )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_INVALIDA );
		
		int quant = conversorUtil.stringParaInteiro( request.getQuantidadeAulasDia() );
		if ( quant < 1 || quant > 5 )
			throw new ValidacaoException( ValidacaoErro.QUANTIDADE_AULAS_DIA_FORA_DA_FAIXA );		
	}
	
	public void validaFiltroRequest( FiltraTurmasRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.DESCRICAO_TURMA_OBRIGATORIO );	
	}
	
}
