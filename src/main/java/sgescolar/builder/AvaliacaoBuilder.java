package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.Matricula;
import sgescolar.model.Nota;
import sgescolar.model.Periodo;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAgendamentoAvaliacaoRequest;
import sgescolar.model.request.SaveNotaRequest;
import sgescolar.model.request.SaveResultadoAvaliacaoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.model.response.NotaResponse;

@Component
public class AvaliacaoBuilder {

	@Autowired
	private NotaBuilder notaBuilder;
	
	@Autowired
	private PeriodoBuilder periodoBuilder;
	
	@Autowired
	private TurmaDisciplinaBuilder turmaDisciplinaBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaAgendamentoAvaliacao( Avaliacao a, SaveAgendamentoAvaliacaoRequest request ) {
		a.setPeso( conversorUtil.stringParaDouble( request.getPeso() ) );
		a.setDataAgendamento( conversorUtil.stringParaData( request.getDataAgendamento() ) );
		a.setNotasDisponiveis( false );
				
		List<Nota> notas = new ArrayList<>();
		
		List<Matricula> matriculas = a.getTurmaDisciplina().getTurma().getMatriculas();
		for( Matricula matricula : matriculas ) {
			Nota nota = notaBuilder.novoNota( matricula, a );
			nota.setNota( 0 );
			
			notas.add( nota );
		}
		
		a.setNotas( notas ); 
	}
	
	public void carregaResultadoAvaliacao( Avaliacao a, SaveResultadoAvaliacaoRequest request ) {
		List<Nota> notas = a.getNotas();
		if ( notas == null )
			a.setNotas( notas = new ArrayList<>() );
		
		List<SaveNotaRequest> requestNotas = request.getNotas();
		for( SaveNotaRequest req : requestNotas ) {
			Matricula matricula = new Matricula();
			matricula.setId( req.getMatriculaId() );
			
			Nota nota = notaBuilder.novoNota( matricula, a );
			notaBuilder.carregaNota( nota, req ); 
			
			notas.add( nota );
		}
		
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
		periodoBuilder.carregaPeriodoResponse( resp.getPeriodo(), a.getPeriodo() ); 
	}
	
	public Avaliacao novoAvaliacao( TurmaDisciplina turmaDisciplina, Periodo periodo ) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setTurmaDisciplina( turmaDisciplina ); 
		avaliacao.setPeriodo( periodo ); 
		return avaliacao;
	}
	
	public AvaliacaoResponse novoAvaliacaoResponse() {
		AvaliacaoResponse resp = new AvaliacaoResponse();
		resp.setTurmaDisciplina( turmaDisciplinaBuilder.novoTurmaDisciplinaResponse() );
		resp.setPeriodo( periodoBuilder.novoPeriodoResponse() );
		return resp;
	}
	
}
