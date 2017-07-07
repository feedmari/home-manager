package view.interfaces;

import view.classes.ManagePaymentPanel;
import view.classes.ManagePaymentPanel.IManagePaymentObserver;

/**
 * Interface for {@link ManagePaymentPanel}.
 * 
 * @author marco mancini
 *
 */
public interface IManagePaymentPanel {

	/**
	 * Attach the observer to this panel.
	 * 
	 * @param observer
	 *            the observer
	 */
	void attachObsverver(IManagePaymentObserver observer);

	/**
	 * Set the welcome label.
	 * 
	 * @param setWelcome the welcome string
	 */
	void setWelcomeLb(String setWelcome);

}
