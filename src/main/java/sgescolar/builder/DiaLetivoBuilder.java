package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.model.Bimestre;
import sgescolar.model.DiaLetivo;
import sgescolar.model.request.SaveDiaLetivoRequest;
import sgescolar.model.response.DiaLetivoResponse;
import sgescolar.util.ConversorUtil;

@Component
public class DiaLetivoBuilder {

	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaDiaLetivo( DiaLetivo dl, SaveDiaLetivoRequest request ) {
		dl.setDataDia( conversorUtil.stringParaData( request.getDataDia() ) ); 
	}
	
	public void carregaDiaLetivoResponse( DiaLetivoResponse resp, DiaLetivo dl ) {
		resp.setId( dl.getId() );
		resp.setDataDia( conversorUtil.dataParaString( dl.getDataDia() ) );		
	}
	
	public DiaLetivo novoDiaLetivo( Bimestre bimestre ) {
		DiaLetivo dl = new DiaLetivo();
		dl.setBimestre( bimestre );
		return dl;
	}
	
	public DiaLetivoResponse novoDiaLetivoResponse() {
		return new DiaLetivoResponse();
	}
	
}