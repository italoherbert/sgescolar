package sgescolar.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.FeriadoManager;
import sgescolar.logica.PeriodoManager;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Escola;
import sgescolar.model.Feriado;
import sgescolar.model.Instituicao;
import sgescolar.model.Periodo;
import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.response.AnoLetivoResponse;
import sgescolar.model.response.FeriadoResponse;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AnoLetivoBuilder {
	
	@Autowired
	private PeriodoBuilder periodoLetivoBuilder;
	
	@Autowired
	private FeriadoBuilder feriadoBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	@Autowired
	private FeriadoManager feriadoUtil;
	
	@Autowired
	private PeriodoManager periodoUtil;
				
	public void carregaAnoLetivo( AnoLetivo al, SaveAnoLetivoRequest request ) {
		al.setAno( conversorUtil.stringParaInteiro( request.getAno() ) );				
	}
	
	public void carregaAnoLetivoResponse( AnoLetivoResponse resp, AnoLetivo al ) {
		Escola e = al.getEscola();
		Instituicao inst = e.getInstituicao();
		
		resp.setId( al.getId() );
		resp.setInstituicaoId( inst.getId() );
		resp.setEscolaId( e.getId() );
		resp.setEscolaNome( e.getNome() ); 
		resp.setAno( conversorUtil.inteiroParaString( al.getAno() ) );
		
		List<Periodo> periodos = al.getPeriodos();
		
		List<PeriodoResponse> periodosResponses = new ArrayList<>();
		for( Periodo pl : periodos ) {		
			PeriodoResponse plresp = periodoLetivoBuilder.novoPeriodoResponse();
			periodoLetivoBuilder.carregaPeriodoResponse( plresp, pl );
			periodosResponses.add( plresp );
		}		
		resp.setPeriodos( periodosResponses );
		
		List<FeriadoResponse> fresps = new ArrayList<>();
		
		List<Feriado> feriados = al.getFeriados();
		for( Feriado f : feriados ) {
			FeriadoResponse fresp = feriadoBuilder.novoFeriadoResponse();
			feriadoBuilder.carregaFeriadoResponse( fresp, f ); 
			fresps.add( fresp );
		}
		resp.setFeriados( fresps );
		
		int soma = 0;
		for( Periodo p : periodos ) {			
			Date dataInicio = p.getDataInicio();
			Date dataFim = p.getDataFim();			
			List<Date> listaDiasFeriados = feriadoUtil.listaDiasFeriados( feriados );
			
			soma += periodoUtil.contaDiasLetivos( dataInicio, dataFim, listaDiasFeriados );
		}
		resp.setDiasLetivosQuant( conversorUtil.inteiroParaString( soma ) );
	}
	
	public AnoLetivo novoAnoLetivo( Escola escola ) {
		AnoLetivo al = new AnoLetivo();
		al.setEscola( escola );
		return al;
	}
	
	public AnoLetivoResponse novoAnoLetivoResponse() {
		return new AnoLetivoResponse();
	}
	
}

