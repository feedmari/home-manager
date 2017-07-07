package view.interfaces;

import java.util.List;

import model.classes.Habitation;
import view.classes.ShowHabitationPanel;
import view.classes.ShowHabitationPanel.IShowHabitationsObserver;

/**
 * Interface for {@link ShowHabitationPanel}.
 * @author marco
 *
 */
public interface IShowHabitationsPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(final IShowHabitationsObserver observer);
	
	/**
	 * Set and show the habitation's list of an user.
	 * @param list the habitation list
	 */
	void setHabitationsList(final List<Habitation> list);
	
	
}
