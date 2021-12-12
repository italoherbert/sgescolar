package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Avaliacao;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAgendamentoAvaliacaoRequest;
import sgescolar.model.request.SaveNotaRequest;
import sgescolar.model.request.SaveResultadoAvaliacaoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.model.response.NotaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AvaliacaoBuilder {

	@Autowired
	private NotaBuilder notaBuilder;
	
	@Autowired
	private TurmaDisciplinaBuilder turmaDisciplinaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaAgendamentoAvaliacao( Avaliacao a, SaveAgendamentoAvaliacaoRequest request ) {
		a.setPeso( conversorUtil.stringParaDouble( request.getPeso() ) );
		a.setDataAgendamento( conversorUtil.stringParaData( request.getDataAgendamento() ) );
		a.setNotasDisponiveis( false );
	}
	
	public void carregaResultadoAvaliacao( Avaliacao a, SaveResultadoAvaliacaoRequest request ) {
		List<Nota> notas = new ArrayList<>();
		
		List<SaveNotaRequest> requestNotas = request.getNotas();
		for( SaveNotaRequest req : requestNotas ) {
			Matricula matricula = new Matricula();
			matricula.setId( req.getMatriculaId() );
			
			Nota nota = notaBuilder.novoNota( matricula, a );
			notaBuilder.carregaNota( nota, req ); 
		}
		
		a.setNotas( notas ); 
		a.setNotasDisponiveis( true );
	}
	
	public void carregaAvaliacaoResponse( AvaliacaoResponse resp, Avaliacao a ) {
		resp.setId( a.getId() );
		resp.setPeso( conversorUtil.doubleParaString( a.getPeso() ) );
		resp.setDataAgendamento( conversorUtil.dataParaString( a.getDataAgendamento() ) );
		resp.setNotasDisponiveis( conversorUtil.booleanParaString( a.isNotasDisponiveis() ) );
		
		List<NotaResponse> notasLista = new ArrayList<>();
		
		List<Nota> notas = a.getNotas();
		for( Nota n : notas ) {
			NotaResponse nresp = notaBuilder.novoNotaResponse();
			notaBuilder.carregaNotaResponse( nresp, n ); 
			notasLista.add( nresp );
		}
		resp.setNotas( notasLista );
		
		turmaDisciplinaBuilder.carregaTurmaDisciplinaResponse( resp.getTurmaDisciplina(), a.getTurmaDisciplina() );
	}
	
	public Avaliacao novoAvaliacao( TurmaDisciplina turmaDisciplina ) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setTurmaDisciplina( turmaDisciplina ); 
		return avaliacao;
	}
	
	public AvaliacaoResponse novoAvaliacaoResponse() {
		AvaliacaoResponse resp = new AvaliacaoResponse();
		resp.setTurmaDisciplina( turmaDisciplinaBuilder.novoTurmaDisciplinaResponse() );
		return resp;
	}
	
}