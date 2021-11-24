package sgescolar.enums;

import sgescolar.model.response.TipoArrayResponse;
import sgescolar.model.response.TipoResponse;

public abstract class AbstractEnumManager<T extends Enum<T>> {
	
	protected abstract T[] values();
		
	protected abstract String label( T e );
	
	public boolean enumValida( String tipo ) {
		if ( tipo == null )
			return false;
		
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return true;
		return false;
	}
	
	public String getEnumString( T tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo == valores[ i ] )
				return valores[ i ].name();	
		
		return null;
	}
			
	public T getEnum( String tipo ) {
		if ( tipo == null )
			return null;
			
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return valores[ i ];		

		return null;
	}
		
	public String[] valores() {
		T[] values = this.values();
		String[] valores = new String[ values.length ];
		for( int i = 0; i < values.length; i++ )
			valores[ i ] = String.valueOf( values[ i ] );
		return valores;
	}
	
	public TipoArrayResponse tipoArrayResponse() {
		T[] values = this.values();
		String[] names = new String[ values.length ];
		String[] labels = new String[ values.length ];
		
		for( int i = 0; i < values.length; i++ ) {
			names[ i ] = values[ i ].name();
			labels[ i ] = this.label( values[ i ] );
		}
		
		TipoArrayResponse resp = new TipoArrayResponse();
		resp.setNames( names );
		resp.setLabels( labels ); 
		return resp;
	}
	
	public TipoResponse tipoResponse( T tipo ) {
		TipoResponse resp = new TipoResponse();
		if ( tipo == null ) {
			resp.setName( "" ); 
			resp.setLabel( "" );
		} else {
			resp.setName( tipo.name() );
			resp.setLabel( this.label( tipo ) );
		}
		return resp;
	}
	
}
