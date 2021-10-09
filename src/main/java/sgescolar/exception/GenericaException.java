package sgescolar.exception;

public class GenericaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public String[] params;
		
	public void setParams( String... params ) {
		this.params = params;
	}
	
}
