package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoTipo;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveAvaliacaoResultadoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AvaliacaoResultadoValidator {
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;

	public void validaSaveRequest( SaveAvaliacaoResultadoRequest request ) throws ValidacaoException {
		if ( request.getMatriculaId() == null )
			throw new ValidacaoException( ValidacaoErro.MATRICULA_OBRIGATORIA );
		
		if ( request.getAvaliacaoTipo() == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_TIPO_OBRIGATORIO );
		
		AvaliacaoTipo atipo = avaliacaoTipoEnumManager.getEnum( request.getAvaliacaoTipo() );
		if ( atipo == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_TIPO_NAO_RECONHECIDO );
		
		switch( atipo ) {
			case NOTA:
				if ( request.getResultado() == null )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_NOTA_OBRIGATORIA );
				if ( request.getResultado().isBlank() )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_NOTA_OBRIGATORIA );				
				
				if ( !validatorUtil.doubleValido( request.getResultado() ) )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_NOTA_INVALIDA );
				break;
			case CONCEITUAL:
				if ( request.getResultado() == null )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_CONCEITO_OBRIGATORIA );
				if ( request.getResultado().isBlank() )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_CONCEITO_OBRIGATORIA );
								
				if ( !avaliacaoConceitoEnumManager.enumValida( request.getResultado() ) )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_CONCEITO_NAO_RECONHECIDO );
				break;
			case DESCRITIVA:
				if ( request.getResultado() == null )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_DESCRICAO_OBRIGATORIA );
				if ( request.getResultado().isBlank() )
					throw new ValidacaoException( ValidacaoErro.AVALIACAO_DESCRICAO_OBRIGATORIA );				
				break;
		}
	}	
}
