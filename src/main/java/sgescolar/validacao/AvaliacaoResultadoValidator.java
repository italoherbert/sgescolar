package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoMetodoEnumManager;
import sgescolar.enums.tipos.AvaliacaoMetodo;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveAvaliacaoResultadoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AvaliacaoResultadoValidator {
		
	@Autowired
	private AvaliacaoMetodoEnumManager avaliacaoMetodoEnumManager;
		
	@Autowired
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveAvaliacaoResultadoRequest request ) throws ValidacaoException {
		if ( request.getMatriculaId() == null )
			throw new ValidacaoException( ValidacaoErro.MATRICULA_OBRIGATORIA );
		
		if ( request.getAvaliacaoMetodo() == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_TIPO_OBRIGATORIO );
		
		AvaliacaoMetodo avMetodo = avaliacaoMetodoEnumManager.getEnum( request.getAvaliacaoMetodo() );
		if ( avMetodo == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_METODO_NAO_RECONHECIDO );
				
		switch( avMetodo ) {
			case NUMERICA:
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
