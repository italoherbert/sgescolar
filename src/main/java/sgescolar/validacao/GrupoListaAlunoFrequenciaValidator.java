package sgescolar.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.BuscaGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveGrupoListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class GrupoListaAlunoFrequenciaValidator {

	@Autowired
	private ListaAlunoFrequenciaValidator listaAlunoFrequenciaValidator;
		
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveGrupoListaAlunoFrequenciaRequest request ) throws ValidacaoException {
		if ( request.getListas() == null )
			throw new ValidacaoException( ValidacaoErro.GRUPO_LISTAS_NULA );
					 				
		SaveListaAlunoFrequenciaRequest[] reqs = request.getListas();
		for( SaveListaAlunoFrequenciaRequest req : reqs )
			listaAlunoFrequenciaValidator.validaSaveRequest( req ); 
	}
	
	public void validaBuscaRequest( BuscaGrupoListaAlunoFrequenciaRequest request ) throws ValidacaoException {		
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );			
	}
	
}

