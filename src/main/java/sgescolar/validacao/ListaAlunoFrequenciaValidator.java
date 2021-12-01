package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.TurnoEnumManager;
import sgescolar.model.request.BuscaListaAlunoFrequenciaRequest;
import sgescolar.model.request.SaveAlunoFrequenciaRequest;
import sgescolar.model.request.SaveListaAlunoFrequenciaRequest;
import sgescolar.msg.ValidacaoErro;
import sgescolar.util.ConversorUtil;
import sgescolar.util.ValidatorUtil;

@Component
public class ListaAlunoFrequenciaValidator {

	@Autowired
	private AlunoFrequenciaValidator alunoFrequenciaValidator;
	
	@Autowired
	private TurnoEnumManager turnoEnumManager;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void validaSaveRequest( SaveListaAlunoFrequenciaRequest request ) throws ValidacaoException {
		if ( request.getFrequencias() == null )
			throw new ValidacaoException( ValidacaoErro.LISTA_ALUNO_FREQUENCIAS_NULA );
		
		if ( request.getNumeroAula() == null )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIO );
		if ( request.getNumeroAula().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_OBRIGATORIO );
			
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );
		if ( !validatorUtil.intValido( request.getNumeroAula() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_INVALIDO );
		
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );		
		if ( numeroAula < 1 || numeroAula > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_FORA_DA_FAIXA );
		
		if ( !turnoEnumManager.enumValida( request.getTurno() ) )
			throw new ValidacaoException( ValidacaoErro.TURNO_NAO_RECONHECIDO );
		
		List<SaveAlunoFrequenciaRequest> reqs = request.getFrequencias();
		for( SaveAlunoFrequenciaRequest req : reqs )
			alunoFrequenciaValidator.validaSaveRequest( req ); 
	}
	
	public void validaBuscaRequest( BuscaListaAlunoFrequenciaRequest request ) throws ValidacaoException {
		if ( !turnoEnumManager.enumValida( request.getTurno() ) )
			throw new ValidacaoException( ValidacaoErro.TURNO_NAO_RECONHECIDO );
		
		if ( !validatorUtil.intValido( request.getNumeroAula() ) )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_INVALIDO );
		if ( !validatorUtil.dataValida( request.getDataDia() ) )
			throw new ValidacaoException( ValidacaoErro.DIA_LETIVO_DATA_INVALIDA );
		
		int numeroAula = conversorUtil.stringParaInteiro( request.getNumeroAula() );		
		if ( numeroAula < 1 || numeroAula > 5 )
			throw new ValidacaoException( ValidacaoErro.AULA_NUMERO_FORA_DA_FAIXA );
	}
	
}
