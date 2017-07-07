package exceptions;

/**
 * Exception for check if user really exists or not.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class InexistentUserException extends Exception {

	private static final long serialVersionUID = 6213410213134458010L;
	
	/**
	 * Constructor.
	 * @param msg the message
	 */
	public InexistentUserException(final String msg) {
		super(msg);
	}

}
