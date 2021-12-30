package sgescolar.relatorio.jrdatasource;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import sgescolar.model.PlanejamentoObjetivo;

public class PlanejamentoObjetivoJRDataSource implements JRDataSource {

	private List<PlanejamentoObjetivo> lista;
	private int i = -1;
	private int size = 0;
	
	public PlanejamentoObjetivoJRDataSource( List<PlanejamentoObjetivo> lista ) {
		this.lista = lista;
		this.i = -1;
		this.size = lista.size();
	}
	
	@Override
	public boolean next() throws JRException {
		return ++i < size;
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		String name = jrField.getName();
	
		Object value = null;
		PlanejamentoObjetivo obj = lista.get( i );
		if ( name.equals( "ID" ) ) {
			value = String.valueOf( obj.getId() );
		} else if ( name.equals( "Objetivo" ) ) {
			value = obj.getObjetivo();		
		}
		
		return ( value == null ? "" : value );
	}

}

