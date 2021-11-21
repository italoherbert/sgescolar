package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PeriodoEnumManager;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Periodo;
import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.PeriodoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PeriodoBuilder {
		
	@Autowired
	private PeriodoEnumManager periodoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;	
	
	public void carregaPeriodo( Periodo b, SavePeriodoRequest request ) {
		b.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		b.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
		b.setLancamentoDataInicio( conversorUtil.stringParaData( request.getLancamentoDataInicio() ) );
		b.setLancamentoDataFim( conversorUtil.stringParaData( request.getLancamentoDataFim() ) );		
		b.setTipo( periodoEnumManager.getEnum( request.getTipo() ) ); 
	}
	
	public void carregaPeriodoResponse( PeriodoResponse resp, Periodo b ) {
		resp.setId( b.getId() );
		resp.setDataInicio( conversorUtil.dataParaString( b.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( b.getDataFim() ) );
		resp.setLancamentoDataInicio( conversorUtil.dataParaString( b.getLancamentoDataInicio() ) );
		resp.setLancamentoDataFim( conversorUtil.dataParaString( b.getLancamentoDataFim() ) );
		resp.setTipo( periodoEnumManager.getString( b.getTipo() ) ); 		
	}
	
	public Periodo novoPeriodo( AnoLetivo anoLetivo ) {
		Periodo b = new Periodo();
		b.setAnoLetivo( anoLetivo );
		return b;
	}
	
	public PeriodoResponse novoPeriodoResponse() {
		return new PeriodoResponse();
	}
	
}
