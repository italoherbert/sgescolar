package sgescolar.util.enums;

import sgescolar.exception.GenericaException;

public abstract class AbstractEnumConversor<T extends Enum<T>, T2 extends GenericaException> {
	
	protected abstract T2 novaException();
	
	protected abstract T[] values();
	
	public String getString( T tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo == valores[ i ] )
				return valores[ i ].name();	
		
		return null;
	}
	
	public T getEnum( String tipo ) throws T2 {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return valores[ i ];		

		throw this.novaException();
	}
		
	public String[] valores() {
		T[] values = this.values();
		String[] valores = new String[ values.length ];
		for( int i = 0; i < values.length; i++ )
			valores[ i ] = String.valueOf( values[ i ] );
		return valores;
	}
	
}
