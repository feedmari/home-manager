package view.interfaces;

import view.classes.RegisterPanel;
import view.classes.RegisterPanel.IRegisterPanelObserver;

/**
 * Interface for {@link RegisterPanel}.
 * @author marco mancini
 *
 */
public interface IRegisterPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(final IRegisterPanelObserver observer);

}
