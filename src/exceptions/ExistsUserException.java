package exceptions;

/**
 * Exception for check in user already exists.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class ExistsUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8680728981924081800L;

	/**
	 * Constructor.
	 * @param msg the message
	 */
	public ExistsUserException(final String msg) {
		super(msg);
		
	}
}
