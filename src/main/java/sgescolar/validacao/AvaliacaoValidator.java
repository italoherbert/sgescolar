package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoTipo;
import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveAvaliacaoAgendamentoRequest;
import sgescolar.model.request.SaveAvaliacaoResultadoGrupoRequest;
import sgescolar.model.request.SaveAvaliacaoResultadoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AvaliacaoValidator {

	@Autowired
	private AvaliacaoResultadoValidator resultadoValidator;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
		
	public void validaAgendamentoSaveRequest( SaveAvaliacaoAgendamentoRequest request ) throws ValidacaoException {
		if ( request.getDataAgendamento() == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_OBRIGATORIO );
		if ( request.getDataAgendamento().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_OBRIGATORIO );
						
		if ( !validatorUtil.doubleValido( request.getPeso() ) )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_INVALIDO );
		if ( !validatorUtil.dataValida( request.getDataAgendamento() ) )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_INVALIDA );
		
		AvaliacaoTipo avTipo = avaliacaoTipoEnumManager.getEnum( request.getAvaliacaoTipo() );
		if ( avTipo == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_TIPO_NAO_RECONHECIDO );
		
		if ( avTipo != AvaliacaoTipo.PROVA_ESCRITA ) {
			if ( request.getPeso() == null )
				throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_OBRIGATORIO );
			if ( request.getPeso().isBlank() )
				throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_OBRIGATORIO );
			
			if ( !validatorUtil.doubleValido( request.getPeso() ) )
				throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_INVALIDO );			
		}
	}
	
	public void validaResultadoSaveRequest( SaveAvaliacaoResultadoGrupoRequest request ) throws ValidacaoException {
		if ( request.getResultados() == null )
			throw new ValidacaoException( ValidacaoErro.RESULTADOS_LISTA_NULA );
		
		List<SaveAvaliacaoResultadoRequest> resultados = request.getResultados();
		for( SaveAvaliacaoResultadoRequest nreq : resultados )
			resultadoValidator.validaSaveRequest( nreq ); 
	}
	
}
