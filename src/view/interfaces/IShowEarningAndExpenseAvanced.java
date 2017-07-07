package view.interfaces;

import view.classes.ShowEarningAndExpenseAvanced;
import view.classes.ShowEarningAndExpenseAvanced.IShowEarningAndExpenseAvancedObserver;

/**
 * Interface for {@link ShowEarningAndExpenseAvanced}.
 * @author Marco Mancini
 *
 */
public interface IShowEarningAndExpenseAvanced  {
	
	/**
	 * Attach the observer.
	 * @param observer the observer
	 */
	void attachObserver(IShowEarningAndExpenseAvancedObserver observer);

}
