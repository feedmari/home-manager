package view.interfaces;

import view.classes.AddHabitationPanel;
import view.classes.AddHabitationPanel.IAddHabitationObsersver;

/**
 * Interface for {@link AddHabitationPanel}.
 * @author Marco Mancini
 *
 */
public interface IAddHabitationPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(IAddHabitationObsersver observer);
	
	/**
	 * Set the owner's textfield.
	 * @param owner the owner
	 */
	void setOwner(final String owner);
	
	/**
	 * set the id textfield.
	 * @param id the id
	 */
	void setId(final int id);
	
	

}
