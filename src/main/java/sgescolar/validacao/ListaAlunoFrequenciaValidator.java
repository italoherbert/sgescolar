package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.model.request.filtro.FiltraListaAlunoFrequenciaRequest;
import sgescolar.msg.ValidacaoErro;

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
	
	public void validaBuscaRequest( FiltraListaAlunoFrequenciaRequest request ) throws ValidacaoException {		
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );				
	}
	
}
