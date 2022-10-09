package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.AvaliacaoConceitoEnumManager;
import sgescolar.enums.AvaliacaoMetodoEnumManager;
import sgescolar.enums.AvaliacaoTipoEnumManager;
import sgescolar.enums.tipos.AvaliacaoConceito;
import sgescolar.enums.tipos.AvaliacaoMetodo;
import sgescolar.enums.tipos.AvaliacaoTipo;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.Avaliacao;
import sgescolar.model.AvaliacaoResultado;
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
	private AvaliacaoConceitoEnumManager avaliacaoConceitoEnumManager;
	
	@Autowired
	private AvaliacaoTipoEnumManager avaliacaoTipoEnumManager;
	
	@Autowired
	private AvaliacaoMetodoEnumManager avaliacaoMetodoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	public void carregaAgendamentoAvaliacao( Avaliacao a, SaveAvaliacaoAgendamentoRequest request ) {		
		AvaliacaoTipo avTipo = avaliacaoTipoEnumManager.getEnum( request.getAvaliacaoTipo() );
		AvaliacaoMetodo avMetodo = a.getTurmaDisciplina().getTurma().getSerie().getCurso().getAvaliacaoMetodo();
				
		a.setAvaliacaoMetodo( avMetodo ); 		
		a.setAvaliacaoTipo( avTipo );
		
		a.setPeso(  conversorUtil.stringParaDouble( request.getPeso() ) );
				
		a.setDataAgendamento( conversorUtil.stringParaData( request.getDataAgendamento() ) );
		a.setResultadoDisponivel( false );		
		
		List<AvaliacaoResultado> resultados = a.getResultados();
		if ( resultados != null ) {
			resultados.clear();
		} else {
			resultados = new ArrayList<>();
		}
				
		List<Matricula> matriculas = a.getTurmaDisciplina().getTurma().getMatriculas();
		for( Matricula matricula : matriculas ) {
			if ( matricula.isEncerrada() )
				continue;
			
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
		
		AvaliacaoMetodo avMetodo = a.getAvaliacaoMetodo();
										
		List<SaveAvaliacaoResultadoRequest> requestResultados = request.getResultados();
		for( SaveAvaliacaoResultadoRequest req : requestResultados ) {
			Matricula matricula = new Matricula();
			matricula.setId( req.getMatriculaId() );
									
			AvaliacaoResultado resultado = avaliacaoResultadoBuilder.novoAvaliacaoResultado( matricula, a );
			avaliacaoResultadoBuilder.carregaAvaliacaoResultado( resultado, req, avMetodo ); 
			
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
						
		resp.setConceitoTipos( avaliacaoConceitoEnumManager.tipoArrayResponse() );		
		resp.setAvaliacaoMetodo( avaliacaoMetodoEnumManager.tipoResponse( a.getAvaliacaoMetodo() ) );
		resp.setAvaliacaoTipo( avaliacaoTipoEnumManager.tipoResponse( a.getAvaliacaoTipo() ) ); 
		
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
