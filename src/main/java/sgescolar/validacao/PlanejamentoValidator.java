package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PlanejamentoTipoEnumManager;
import sgescolar.model.request.SavePlanejamentoAnexoRequest;
import sgescolar.model.request.SavePlanejamentoConteudoRequest;
import sgescolar.model.request.SavePlanejamentoObjetivoRequest;
import sgescolar.model.request.SavePlanejamentoRequest;
import sgescolar.model.request.filtro.FiltraPlanejamentosRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class PlanejamentoValidator {
	
	@Autowired
	private PlanejamentoObjetivoValidator planejamentoObjetivoValidator;
	
	@Autowired
	private PlanejamentoConteudoValidator planejamentoConteudoValidator;
	
	@Autowired
	private PlanejamentoAnexoValidator planejamentoAnexoValidator;
	
	@Autowired
	private PlanejamentoTipoEnumManager planejamentoTipoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SavePlanejamentoRequest request ) throws ValidacaoException {
		if ( request.getDescricao() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DESCRICAO_OBRIGATORIA );
		if ( request.getDescricao().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DESCRICAO_OBRIGATORIA );
		
		if ( request.getDataInicio() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_INICIO_OBRIGATORIA );
		if ( request.getDataInicio().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_INICIO_OBRIGATORIA );
		
		if ( request.getDataFim() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_FIM_OBRIGATORIA );
		if ( request.getDataFim().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_FIM_OBRIGATORIA );
		
		if ( !validatorUtil.dataValida( request.getDataInicio() ) )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_INICIO_INVALIDA );
		if ( !validatorUtil.dataValida( request.getDataFim() ) )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DATA_FIM_INVALIDA );
		
		if ( !planejamentoTipoEnumManager.enumValida( request.getTipo() ) )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_TIPO_NAO_RECONHECIDO );
		
		List<SavePlanejamentoObjetivoRequest> objReqs = request.getObjetivos();
		List<SavePlanejamentoConteudoRequest> conReqs = request.getConteudos();
		List<SavePlanejamentoAnexoRequest> anexosReqs = request.getAnexos();
		
		if ( objReqs == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_OBJETIVOS_LISTA_NULA );
		if ( conReqs == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_CONTEUDOS_LISTA_NULA );
		if ( anexosReqs == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_ANEXOS_LISTA_NULA );
		
		for( SavePlanejamentoObjetivoRequest req : objReqs )
			planejamentoObjetivoValidator.validaSaveRequest( req );		
		for( SavePlanejamentoConteudoRequest req : conReqs )
			planejamentoConteudoValidator.validaSaveRequest( req );		
		for( SavePlanejamentoAnexoRequest req : anexosReqs )
			planejamentoAnexoValidator.validaSaveRequest( req ); 
	}
	
	public void validaFiltroRequest( FiltraPlanejamentosRequest request ) throws ValidacaoException {
		if ( request.getDescricaoIni() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DESCRICAO_OBRIGATORIA );
		if ( request.getDescricaoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_DESCRICAO_OBRIGATORIA );
		
		if ( request.getIntervaloData() == null )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_INTERVALO_DATA_OBRIGATORIA );
		if ( request.getIntervaloData().isBlank() )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_INTERVALO_DATA_OBRIGATORIA );
				
		if ( !validatorUtil.dataValida( request.getIntervaloData() ) )
			throw new ValidacaoException( ValidacaoErro.PLANEJAMENTO_INTERVALO_DATA_INVALIDA );

	}
	
}
