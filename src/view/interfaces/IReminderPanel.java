package view.interfaces;

import view.classes.ReminderPanel;
import view.classes.ReminderPanel.IReminderPanelObserver;

/**
 * Interface for {@link ReminderPanel}.
 * @author Marco Mancini
 *
 */
public interface IReminderPanel {
	
	/**
	 * Aattach the observer to te panel.
	 * @param observer the observer
	 */
	void attachObserver(IReminderPanelObserver observer);
	
	/**
	 * Refresh the tables.
	 */
	void refreshTables();
	
	/**
	 * Adds a row to the earning's table.
	 * @param obj the row
	 */
	void addEarningRow(final Object[] obj);
	
	/**
	 * Add a row to the expense's table.
	 * @param obj the row
	 */
	void addExpenseRow(final Object[] obj);
	
	/**
	 * Remove all elements from the table.
	 */
	void removeAll();

}
