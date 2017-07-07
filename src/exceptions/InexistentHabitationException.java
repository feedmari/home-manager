package exceptions;

/**
 * Exception for check if habitation really exists or not.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class InexistentHabitationException extends Exception {

	private static final long serialVersionUID = 6213410213534458010L;
	
	/**
	 * Constructor.
	 * @param msg the message
	 */
	public InexistentHabitationException(final String msg) {
		super(msg);
	}

}
