package view.interfaces;

import view.classes.LoginPanel;
import view.classes.LoginPanel.ILoginPanelObserver;

/**
 * Interface for {@link LoginPanel}.
 * @author marco mancini
 *
 */

public interface ILoginPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(ILoginPanelObserver observer);
	


}
