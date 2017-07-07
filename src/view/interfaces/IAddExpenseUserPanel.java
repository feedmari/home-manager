package view.interfaces;

import view.classes.AddExpenseUserPanel.IAddExpenseUserObserver;
import view.classes.AddExpensesPanel;

/**
 * Interface for {@link AddExpensesPanel}.
 * @author marco mancini
 *
 */
public interface IAddExpenseUserPanel {

		/**
		 * Attach the observer to this panel.
		 * @param observer the observer
		 */
		void attachObserver(IAddExpenseUserObserver observer);


	}
