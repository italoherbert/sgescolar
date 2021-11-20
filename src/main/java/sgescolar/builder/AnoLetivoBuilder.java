package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AnoLetivo;
import sgescolar.model.Bimestre;
import sgescolar.model.Escola;
import sgescolar.model.Feriado;
import sgescolar.model.request.SaveAnoLetivoRequest;
import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.model.response.AnoLetivoResponse;
import sgescolar.model.response.BimestreResponse;
import sgescolar.model.response.FeriadoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class AnoLetivoBuilder {
	
	@Autowired
	private BimestreBuilder bimestreBuilder;
	
	@Autowired
	private FeriadoBuilder feriadoBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void carregaAnoLetivo( AnoLetivo al, SaveAnoLetivoRequest request ) {
		al.setAno( conversorUtil.stringParaInteiro( request.getAno() ) );
		
		Bimestre primeiroBimestre = bimestreBuilder.novoBimestre( al );
		bimestreBuilder.carregaBimestre( primeiroBimestre, request.getPrimeiroBimestre() );
		al.setPrimeiroBimestre( primeiroBimestre ); 
		
		Bimestre segundoBimestre = bimestreBuilder.novoBimestre( al );
		bimestreBuilder.carregaBimestre( segundoBimestre, request.getSegundoBimestre() );
		al.setSegundoBimestre( segundoBimestre ); 
		
		Bimestre terceiroBimestre = bimestreBuilder.novoBimestre( al );
		bimestreBuilder.carregaBimestre( terceiroBimestre, request.getTerceiroBimestre() );
		al.setTerceiroBimestre( terceiroBimestre ); 
		
		Bimestre quartoBimestre = bimestreBuilder.novoBimestre( al );
		bimestreBuilder.carregaBimestre( quartoBimestre, request.getQuartoBimestre() );
		al.setQuartoBimestre( quartoBimestre ); 
		
		List<SaveFeriadoRequest> feriados = request.getFeriados();
		
		List<Feriado> lista = new ArrayList<>();
		for( SaveFeriadoRequest freq : feriados ) {
			Feriado f = feriadoBuilder.novoFeriado( al );
			feriadoBuilder.carregaFeriado( f, freq );
			lista.add( f );
		}

		al.setFeriados( lista );
	}
	
	public void carregaAnoLetivoResponse( AnoLetivoResponse resp, AnoLetivo al ) {
		resp.setId( al.getId() );
		resp.setAno( conversorUtil.inteiroParaString( al.getAno() ) );
		
		BimestreResponse primeiroBResp = bimestreBuilder.novoBimestreResponse();
		bimestreBuilder.carregaBimestreResponse( primeiroBResp, al.getPrimeiroBimestre() );
		resp.setPrimeiroBimestre( primeiroBResp );
		
		BimestreResponse segundoBResp = bimestreBuilder.novoBimestreResponse();
		bimestreBuilder.carregaBimestreResponse( segundoBResp, al.getSegundoBimestre() );
		resp.setSegundoBimestre( segundoBResp );
		
		BimestreResponse terceiroBResp = bimestreBuilder.novoBimestreResponse();
		bimestreBuilder.carregaBimestreResponse( terceiroBResp, al.getTerceiroBimestre() );
		resp.setTerceiroBimestre( terceiroBResp );
		
		BimestreResponse quartoBResp = bimestreBuilder.novoBimestreResponse();
		bimestreBuilder.carregaBimestreResponse( quartoBResp, al.getQuartoBimestre() );
		resp.setQuartoBimestre( quartoBResp );
		
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

