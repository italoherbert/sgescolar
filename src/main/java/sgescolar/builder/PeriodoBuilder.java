package sgescolar.builder;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.PeriodoEnumManager;
import sgescolar.logica.manager.FeriadoManager;
import sgescolar.logica.manager.PeriodoManager;
import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Feriado;
import sgescolar.model.Periodo;
import sgescolar.model.request.SavePeriodoRequest;
import sgescolar.model.response.PeriodoResponse;

@Component
public class PeriodoBuilder {
		
	@Autowired
	private PeriodoEnumManager periodoEnumManager;
	
	@Autowired
	private ConversorUtil conversorUtil;
		
	@Autowired
	private FeriadoManager feriadoManager;
	
	@Autowired
	private PeriodoManager periodoManager;
	
	public void carregaPeriodo( Periodo p, SavePeriodoRequest request ) {
		p.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		p.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
		p.setLancamentoDataInicio( conversorUtil.stringParaData( request.getLancamentoDataInicio() ) );
		p.setLancamentoDataFim( conversorUtil.stringParaData( request.getLancamentoDataFim() ) );		
		p.setTipo( periodoEnumManager.getEnum( request.getTipo() ) ); 
	}
	
	public void carregaPeriodoResponse( PeriodoResponse resp, Periodo p ) {
		resp.setId( p.getId() );
		resp.setDataInicio( conversorUtil.dataParaString( p.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( p.getDataFim() ) );
		resp.setLancamentoDataInicio( conversorUtil.dataParaString( p.getLancamentoDataInicio() ) );
		resp.setLancamentoDataFim( conversorUtil.dataParaString( p.getLancamentoDataFim() ) );
		resp.setTipo( periodoEnumManager.getEnumString( p.getTipo() ) ); 		

		resp.setDescricao( resp.getDataInicio() + " até " + resp.getDataFim() ); 
		
		AnoLetivo al = p.getAnoLetivo();
		
		List<Feriado> feriados = al.getFeriados();
		Date dataInicio = p.getDataInicio();
		Date dataFim = p.getDataFim();
		
		List<Date> listaDiasFeriados = feriadoManager.listaDiasFeriados( feriados );			
		int quant = periodoManager.contaDiasLetivos( dataInicio, dataFim, listaDiasFeriados );
		
		resp.setDiasLetivosQuant( conversorUtil.inteiroParaString( quant ) ); 		
	}
	
	public Periodo novoPeriodo( AnoLetivo anoLetivo ) {
		Periodo p = new Periodo();
		p.setAnoLetivo( anoLetivo );
		return p;
	}
	
	public PeriodoResponse novoPeriodoResponse() {
		return new PeriodoResponse();
	}
	
}
