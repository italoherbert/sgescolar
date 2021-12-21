package sgescolar.validacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ValidatorUtil;
import sgescolar.model.request.SaveAgendamentoAvaliacaoRequest;
import sgescolar.model.request.SaveNotaRequest;
import sgescolar.model.request.SaveResultadoAvaliacaoRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class AvaliacaoValidator {

	@Autowired
	private NotaValidator notaValidator;
	
	@Autowired
	private ValidatorUtil validatorUtil;
	
	public void validaAgendamentoSaveRequest( SaveAgendamentoAvaliacaoRequest request ) throws ValidacaoException {
		if ( request.getDataAgendamento() == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_OBRIGATORIO );
		if ( request.getDataAgendamento().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_OBRIGATORIO );
		
		if ( request.getPeso() == null )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_OBRIGATORIO );
		if ( request.getPeso().isBlank() )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_OBRIGATORIO );
		
		if ( !validatorUtil.doubleValido( request.getPeso() ) )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_PESO_INVALIDO );
		if ( !validatorUtil.dataValida( request.getDataAgendamento() ) )
			throw new ValidacaoException( ValidacaoErro.AVALIACAO_DATA_AGENDAMENTO_INVALIDA );
	}
	
	public void validaResultadoSaveRequest( SaveResultadoAvaliacaoRequest request ) throws ValidacaoException {
		if ( request.getNotas() == null )
			throw new ValidacaoException( ValidacaoErro.NOTAS_LISTA_NULA );
		
		List<SaveNotaRequest> notas = request.getNotas();
		for( SaveNotaRequest nreq : notas )
			notaValidator.validaSaveRequest( nreq ); 
	}
	
}
