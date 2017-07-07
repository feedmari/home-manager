package view.interfaces;

import view.classes.ShowEarningAndExpensesBase;
import view.classes.ShowEarningAndExpensesBase.IShowEarningAndExpensesBaseObserver;

/**
 * Interface for {@link ShowEarningAndExpensesBase}.
 * @author Marco Mancini
 *
 */
public interface IShowEarningAndExpensesBase {
	
	/**
	 * Attacht the observer.
	 * @param observer the observer
	 */
	void attachObserver(IShowEarningAndExpensesBaseObserver observer);
	
	/**
	 * Refresh the table.
	 */
	void refreshTable();
	
	/**
	 * Adds a row to the table.
	 * @param obj the row
	 */
	void addRow(final Object[] obj);
	
	/**
	 * Remove all elements from the table.
	 */
	void removeAll();

	/**
	 * Create the table.
	 */
	void createTable();
	
	/**
	 * Create buttons.
	 */
	void createButtons();
	
	/**
	 * Set layout and add components.
	 */
	void setLayoutAndAddComp();
	

}
