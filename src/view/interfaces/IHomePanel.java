package view.interfaces;

import view.classes.HomePanel;
import view.classes.HomePanel.IHomePanelObserver;

/**
 * Interface for {@link HomePanel}.
 * @author marco mancini
 *
 */
public interface IHomePanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(IHomePanelObserver observer);
	
	/**
	 * Set the logged user.
	 * @param username the username
	 */
	void setUser(String username);
	

}
