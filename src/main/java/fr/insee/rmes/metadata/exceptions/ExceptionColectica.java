package fr.insee.rmes.metadata.exceptions;

public class ExceptionColectica extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8391641085516396938L;

	public ExceptionColectica(String s) {
        super(s);
    }
    
    public ExceptionColectica(String s, Throwable t) {
        super(s, t);
    }
    
    /**
     * Indicates to the client that the error message is known and displayable
     * @return true
     */
    public boolean isExceptionKnown() {
    	return true;
    }


}
