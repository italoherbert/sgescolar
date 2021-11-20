package sgescolar.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PeriodoLetivoEnumManager;
import sgescolar.model.AnoLetivo;
import sgescolar.model.DiaLetivo;
import sgescolar.model.PeriodoLetivo;
import sgescolar.model.request.SavePeriodoLetivoRequest;
import sgescolar.model.response.DiaLetivoResponse;
import sgescolar.model.response.PeriodoLetivoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PeriodoLetivoBuilder {
	
	@Autowired
	private DiaLetivoBuilder diaLetivoBuilder;
	
	@Autowired
	private PeriodoLetivoEnumManager periodoLetivoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void carregaPeriodoLetivo( PeriodoLetivo b, SavePeriodoLetivoRequest request ) {
		b.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		b.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
		b.setLancamentoDataInicio( conversorUtil.stringParaData( request.getLancamentoDataInicio() ) );
		b.setLancamentoDataFim( conversorUtil.stringParaData( request.getLancamentoDataFim() ) );		
		b.setTipo( periodoLetivoEnumManager.getEnum( request.getTipo() ) ); 
	}
	
	public void carregaPeriodoLetivoResponse( PeriodoLetivoResponse resp, PeriodoLetivo b ) {
		resp.setId( b.getId() );
		resp.setDataInicio( conversorUtil.dataParaString( b.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( b.getDataFim() ) );
		resp.setLancamentoDataInicio( conversorUtil.dataParaString( b.getLancamentoDataInicio() ) );
		resp.setLancamentoDataFim( conversorUtil.dataParaString( b.getLancamentoDataFim() ) );
		resp.setTipo( periodoLetivoEnumManager.getString( b.getTipo() ) ); 
		
		List<DiaLetivo> diasLetivos = b.getDiasLetivos();
		
		List<DiaLetivoResponse> lista = new ArrayList<>();
		for( DiaLetivo dl : diasLetivos ) {
			DiaLetivoResponse dlresp = diaLetivoBuilder.novoDiaLetivoResponse();
			diaLetivoBuilder.carregaDiaLetivoResponse( dlresp, dl ); 
			lista.add( dlresp );
		}
		resp.setAnosLetivos( lista );
	}
	
	public PeriodoLetivo novoPeriodoLetivo( AnoLetivo anoLetivo ) {
		PeriodoLetivo b = new PeriodoLetivo();
		b.setAnoLetivo( anoLetivo );
		return b;
	}
	
	public PeriodoLetivoResponse novoPeriodoLetivoResponse() {
		return new PeriodoLetivoResponse();
	}
	
}
