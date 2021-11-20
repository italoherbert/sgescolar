package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.AnoLetivo;
import sgescolar.model.Bimestre;
import sgescolar.model.DiaLetivo;
import sgescolar.model.request.SaveBimestreRequest;
import sgescolar.model.request.SaveDiaLetivoRequest;
import sgescolar.model.response.BimestreResponse;
import sgescolar.model.response.DiaLetivoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class BimestreBuilder {
	
	@Autowired
	private DiaLetivoBuilder diaLetivoBuilder;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void carregaBimestre( Bimestre b, SaveBimestreRequest request ) {
		b.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		b.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
		
		List<SaveDiaLetivoRequest> diasLetivos = request.getDiasLetivos();
		
		List<DiaLetivo> lista = new ArrayList<>();
		for( SaveDiaLetivoRequest dlreq : diasLetivos ) {
			DiaLetivo dl = diaLetivoBuilder.novoDiaLetivo( b );
			diaLetivoBuilder.carregaDiaLetivo( dl, dlreq );
			lista.add( dl );
		}

		b.setDiasLetivos( lista );
	}
	
	public void carregaBimestreResponse( BimestreResponse resp, Bimestre b ) {
		resp.setId( b.getId() );
		resp.setDataInicio( conversorUtil.dataParaString( b.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( b.getDataFim() ) );
		
		List<DiaLetivo> diasLetivos = b.getDiasLetivos();
		
		List<DiaLetivoResponse> lista = new ArrayList<>();
		for( DiaLetivo dl : diasLetivos ) {
			DiaLetivoResponse dlresp = diaLetivoBuilder.novoDiaLetivoResponse();
			diaLetivoBuilder.carregaDiaLetivoResponse( dlresp, dl ); 
			lista.add( dlresp );
		}
		resp.setAnosLetivos( lista );
	}
	
	public Bimestre novoBimestre( AnoLetivo anoLetivo ) {
		Bimestre b = new Bimestre();
		b.setAnoLetivo( anoLetivo );
		return b;
	}
	
	public BimestreResponse novoBimestreResponse() {
		return new BimestreResponse();
	}
	
}
