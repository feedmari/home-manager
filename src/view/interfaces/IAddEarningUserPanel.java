package view.interfaces;

import view.classes.AddEarningUserPanel;
import view.classes.AddEarningUserPanel.IAddEarningUserObserver;

/**
 * Interface for {@link AddEarningUserPanel}.
 * 
 * @author marinelli
 *
 */
public interface IAddEarningUserPanel {

	/**
	 * Attach the observer to this panel.
	 * 
	 * @param observer
	 *            the observer
	 */
	void attachObserver(IAddEarningUserObserver observer);

}
