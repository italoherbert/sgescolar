package sgescolar.enums;

import sgescolar.model.response.TipoResponse;

public abstract class AbstractEnumManager<T extends Enum<T>> {
	
	protected abstract T[] values();
		
	protected abstract String texto( T e );
	
	public boolean enumValida( String tipo ) {
		if ( tipo == null )
			return false;
		
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return true;
		return false;
	}
	
	public String getTexto( T tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo == valores[ i ] )
				return this.texto( valores[ i ] );	
		
		return null;
	}
	
	public String getName( T tipo ) {
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
	
	public TipoResponse tipoResponse() {
		T[] values = this.values();
		String[] names = new String[ values.length ];
		String[] textos = new String[ values.length ];
		
		for( int i = 0; i < values.length; i++ ) {
			names[ i ] = values[ i ].name();
			textos[ i ] = this.texto( values[ i ] );
		}
		
		TipoResponse resp = new TipoResponse();
		resp.setNames( names );
		resp.setTextos( textos ); 
		return resp;
	}
	
}
