package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoConceito;
import sgescolar.enums.tipos.AvaliacaoTipo;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoResultado;
import sgescolar.model.Curso;
import sgescolar.model.Matricula;
import sgescolar.model.Periodo;
import sgescolar.model.TurmaDisciplina;
import sgescolar.model.request.SaveAvaliacaoAgendamentoRequest;
import sgescolar.model.request.SaveAvaliacaoResultadoGrupoRequest;
import sgescolar.model.request.SaveAvaliacaoResultadoRequest;
import sgescolar.model.response.AvaliacaoResponse;
import sgescolar.model.response.AvaliacaoResultadoResponse;

@Component
public class AvaliacaoBuilder {

	@Autowired
	private AvaliacaoResultadoBuilder avaliacaoResultadoBuilder;
	
	@Autowired
	private PeriodoBuilder periodoBuilder;
	
	@Autowired
	private TurmaDisciplinaBuilder turmaDisciplinaBuilder;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaAgendamentoAvaliacao( Avaliacao a, SaveAvaliacaoAgendamentoRequest request ) {
		a.setPeso( conversorUtil.stringParaDouble( request.getPeso() ) );
		a.setDataAgendamento( conversorUtil.stringParaData( request.getDataAgendamento() ) );
		a.setResultadoDisponivel( false );		
		
		List<AvaliacaoResultado> resultados = a.getResultados();
		if ( resultados != null )
			resultados.clear();
				
		List<Matricula> matriculas = a.getTurmaDisciplina().getTurma().getMatriculas();
		for( Matricula matricula : matriculas ) {
			AvaliacaoResultado resultado = avaliacaoResultadoBuilder.novoAvaliacaoResultado( matricula, a );
			resultado.setNota( 0 );
			resultado.setConceito( AvaliacaoConceito.NAO_DISPONIVEL );
			resultado.setDescricao( "" );
			
			resultados.add( resultado );
		}
		
		a.setResultados( resultados );
	}
	
	public void carregaResultadoAvaliacao( Avaliacao a, SaveAvaliacaoResultadoGrupoRequest request ) {
		List<AvaliacaoResultado> resultados = a.getResultados();
		if ( resultados == null )
			a.setResultados( resultados = new ArrayList<>() );
		
		Curso curso = a.getTurmaDisciplina().getTurma().getSerie().getCurso();
		AvaliacaoTipo atipo = curso.getAvaliacaoTipo();
						
		List<SaveAvaliacaoResultadoRequest> requestResultados = request.getResultados();
		for( SaveAvaliacaoResultadoRequest req : requestResultados ) {
			Matricula matricula = new Matricula();
			matricula.setId( req.getMatriculaId() );
									
			AvaliacaoResultado resultado = avaliacaoResultadoBuilder.novoAvaliacaoResultado( matricula, a );
			avaliacaoResultadoBuilder.carregaAvaliacaoResultado( resultado, req, atipo ); 
			
			resultados.add( resultado );
		}
			
		a.setResultadoDisponivel( true );
	}
	
	public void carregaAvaliacaoResponse( AvaliacaoResponse resp, Avaliacao a ) {
		resp.setId( a.getId() );
		resp.setPeso( conversorUtil.doubleParaString( a.getPeso() ) );
		resp.setDataAgendamento( conversorUtil.dataParaString( a.getDataAgendamento() ) );
		resp.setResultadoDisponivel( conversorUtil.booleanParaString( a.isResultadoDisponivel() ) );
				
		List<AvaliacaoResultadoResponse> resultadosLista = new ArrayList<>();
		
		List<AvaliacaoResultado> resultados = a.getResultados();
		for( AvaliacaoResultado result : resultados ) {
			AvaliacaoResultadoResponse nresp = avaliacaoResultadoBuilder.novoAvaliacaoResultadoResponse();
			avaliacaoResultadoBuilder.carregaAvaliacaoResultadoResponse( nresp, result ); 
			resultadosLista.add( nresp );
		}
		resp.setResultados( resultadosLista );
		
		AvaliacaoTipo atipo = a.getTurmaDisciplina().getTurma().getSerie().getCurso().getAvaliacaoTipo();
		
		resp.setAvaliacaoTipo( avaliacaoTipoEnumManager.tipoResponse( atipo ) );	
		resp.setConceitoTipos( avaliacaoConceitoEnumManager.tipoArrayResponse() ); 
		
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
