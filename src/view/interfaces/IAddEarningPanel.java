package view.interfaces;

import view.classes.AddEarningPanel;
import view.classes.AddEarningPanel.IAddEarningObserver;

/**
 * Interface for {@link AddEarningPanel}.
 * @author marco mancini
 *
 */
public interface IAddEarningPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(IAddEarningObserver observer);

}
