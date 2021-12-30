package sgescolar.relatorio.jrdatasource;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import sgescolar.model.PlanejamentoConteudo;

public class PlanejamentoConteudoJRDataSource implements JRDataSource {

	private List<PlanejamentoConteudo> lista;
	private int i = -1;
	private int size = 0;
	
	public PlanejamentoConteudoJRDataSource( List<PlanejamentoConteudo> lista ) {
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
		
		PlanejamentoConteudo p = lista.get( i );
		if ( name.equals( "ID" ) ) {
			value = String.valueOf( p.getId() );
		} if ( name.equals( "Conteudo" ) ) {
			value = p.getConteudo();		
		}
		
		return ( value == null ? "" : value );
	}

}
