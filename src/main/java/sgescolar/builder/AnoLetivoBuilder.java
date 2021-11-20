package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AnoLetivo;
import sgescolar.model.Escola;
import sgescolar.model.Feriado;
import sgescolar.model.PeriodoLetivo;
import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.response.AnoLetivoResponse;
import sgescolar.model.response.FeriadoResponse;
import sgescolar.model.response.PeriodoLetivoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AnoLetivoBuilder {
	
	@Autowired
	private PeriodoLetivoBuilder periodoLetivoBuilder;
	
	@Autowired
	private FeriadoBuilder feriadoBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void carregaAnoLetivo( AnoLetivo al, SaveAnoLetivoRequest request ) {
		al.setAno( conversorUtil.stringParaInteiro( request.getAno() ) );				
	}
	
	public void carregaAnoLetivoResponse( AnoLetivoResponse resp, AnoLetivo al ) {
		resp.setId( al.getId() );
		resp.setAno( conversorUtil.inteiroParaString( al.getAno() ) );
		
		List<PeriodoLetivo> periodosLetivos = al.getPeriodosLetivos();
		
		List<PeriodoLetivoResponse> periodosResponses = new ArrayList<>();
		for( PeriodoLetivo pl : periodosLetivos ) {		
			PeriodoLetivoResponse plresp = periodoLetivoBuilder.novoPeriodoLetivoResponse();
			periodoLetivoBuilder.carregaPeriodoLetivoResponse( plresp, pl );
			periodosResponses.add( plresp );
		}		
		resp.setPeriodosLetivos( periodosResponses );
		
		List<FeriadoResponse> fresps = new ArrayList<>();
		
		List<Feriado> feriados = al.getFeriados();
		for( Feriado f : feriados ) {
			FeriadoResponse fresp = feriadoBuilder.novoFeriadoResponse();
			feriadoBuilder.carregaFeriadoResponse( fresp, f ); 
			fresps.add( fresp );
		}
		resp.setFeriados( fresps );
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

