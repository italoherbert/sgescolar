package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.request.BuscaListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ValidatorUtil;

@Component
public class ListaAlunoFrequenciaValidator {

	@Autowired
	private AlunoFrequenciaValidator alunoFrequenciaValidator;
		
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaSaveRequest( SaveListaAlunoFrequenciaRequest request ) throws ValidacaoException {
		if ( request.getFrequencias() == null )
			throw new ValidacaoException( ValidacaoErro.LISTA_ALUNO_FREQUENCIAS_NULA );
					
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );
				
		List<SaveAlunoFrequenciaRequest> reqs = request.getFrequencias();
		for( SaveAlunoFrequenciaRequest req : reqs )
			alunoFrequenciaValidator.validaSaveRequest( req ); 
	}
	
	public void validaBuscaRequest( BuscaListaAlunoFrequenciaRequest request ) throws ValidacaoException {		
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );				
	}
	
}
