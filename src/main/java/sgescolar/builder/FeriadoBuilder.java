package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.logica.util.ConversorUtil;
import sgescolar.model.AnoLetivo;
import sgescolar.model.Feriado;
import sgescolar.model.request.SaveFeriadoRequest;
import sgescolar.model.response.FeriadoResponse;

@Component
public class FeriadoBuilder {

	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaFeriado( Feriado f, SaveFeriadoRequest request ) {
		f.setDescricao( request.getDescricao() );
		f.setDataInicio( conversorUtil.stringParaData( request.getDataInicio() ) );
		f.setDataFim( conversorUtil.stringParaData( request.getDataFim() ) );
	}
	
	public void carregaFeriadoResponse( FeriadoResponse resp, Feriado f ) {
		resp.setId( f.getId() );
		resp.setDescricao( f.getDescricao() );
		resp.setDataInicio( conversorUtil.dataParaString( f.getDataInicio() ) );
		resp.setDataFim( conversorUtil.dataParaString( f.getDataFim() ) );
	}
	
	public Feriado novoFeriado( AnoLetivo anoLetivo ) {
		Feriado f = new Feriado();
		f.setAnoLetivo( anoLetivo );
		return f;
	}
	
	public FeriadoResponse novoFeriadoResponse() {
		return new FeriadoResponse();
	}
	
}


