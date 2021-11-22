package sgescolar.enums;

import sgescolar.model.response.TipoResponse;

public abstract class AbstractEnumManager<T extends Enum<T>> {
	
	protected abstract T[] values();
		
	protected abstract String descricao( T e );
	
	public boolean enumValida( String tipo ) {
		if ( tipo == null )
			return false;
		
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
		String[] valores = new String[ values.length ];
		String[] descricoes = new String[ values.length ];
		
		for( int i = 0; i < values.length; i++ ) {
			valores[ i ] = values[ i ].name();
			descricoes[ i ] = this.descricao( values[ i ] );
		}
		
		TipoResponse resp = new TipoResponse();
		resp.setValores( valores );
		resp.setDescricoes( descricoes ); 
		return resp;
	}
	
}
