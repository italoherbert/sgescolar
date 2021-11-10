package sgescolar.msg;

public class SistemaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String[] params;
	
	public SistemaException( String msg, String... params ) {
		super( msg );
		this.params = params;
	}
	
	public String getMensagem() {
		String novaMsg = super.getMessage();
		for( int i = 0; i < params.length; i++ )
			novaMsg = novaMsg.replace( "$"+(i+1), params[ i ] );
		return novaMsg;
	}

}
