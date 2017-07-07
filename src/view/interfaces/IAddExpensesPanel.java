package view.interfaces;

import view.classes.AddExpensesPanel;
import view.classes.AddExpensesPanel.IAddExpensesObserver;

/**
 * Interface for {@link AddExpensesPanel}.
 * @author marco mancini
 *
 */
public interface IAddExpensesPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(IAddExpensesObserver observer);


}
