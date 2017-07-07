package view.classes;

/**
 * The panel for manage habitations.
 * @author federico marinelli
 */
public class ManageUserPaymentPanel extends ManagePaymentPanel {

	
	private static final long serialVersionUID = -1979192807962861600L;
	
	/**
	 * Constructor.
	 */
	public ManageUserPaymentPanel() {
		super.getShowCalendar().setVisible(false);
		super.getShowCalendarBtn().setVisible(false);
	}
}
