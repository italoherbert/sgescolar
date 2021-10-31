package sgescolar.enums;

public abstract class AbstractEnumManager<T extends Enum<T>> {
	
	protected abstract T[] values();
	
	public boolean enumValida( String tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return true;
		return false;
	}
	
	public String getString( T tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo == valores[ i ] )
				return valores[ i ].name();	
		
		return null;
	}
	
	public T getEnum( String tipo ) {
		T[] valores = this.values();
		for( int i = 0; i < valores.length; i++ )
			if ( tipo.equalsIgnoreCase( valores[ i ].name() ) )
				return valores[ i ];		

		throw null;
	}
		
	public String[] valores() {
		T[] values = this.values();
		String[] valores = new String[ values.length ];
		for( int i = 0; i < values.length; i++ )
			valores[ i ] = String.valueOf( values[ i ] );
		return valores;
	}
	
}
